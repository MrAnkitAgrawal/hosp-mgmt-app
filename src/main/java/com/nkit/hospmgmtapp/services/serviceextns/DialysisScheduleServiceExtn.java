package com.nkit.hospmgmtapp.services.serviceextns;

import static com.nkit.hospmgmtapp.domain.entities.DialysisStationStatus.ACTIVE;
import static com.nkit.hospmgmtapp.domain.entities.ScheduleStatus.*;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

import com.nkit.hospmgmtapp.domain.entities.*;
import com.nkit.hospmgmtapp.domain.repos.DialysisScheduleR;
import com.nkit.hospmgmtapp.domain.repos.DialysisSlotR;
import com.nkit.hospmgmtapp.domain.repos.DialysisStationR;
import com.nkit.hospmgmtapp.resources.models.DialysisStatusUpdateRequestDto;
import com.nkit.hospmgmtapp.services.models.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialysisScheduleServiceExtn {
  private final long DELAY_ACCEPTED_IN_HRS = 2;

  private final DialysisStationR dialysisStationR;
  private final DialysisSlotR dialysisSlotR;
  private final DialysisScheduleR dialysisScheduleR;

  public DialysisScheduleE bookDialysisSchedule(
      PatientE patientE,
      LocalDate scheduleDate,
      DialysisStationE dStationE,
      DialysisSlotE dSlot,
      boolean scheduleRandomly) {
    List<Schedule> validSchedules = availableSchedulesForDate(scheduleDate);

    if (validSchedules == null || validSchedules.isEmpty()) {
      throw new RuntimeException(NO_DIALYSIS_SCHEDULE_AVAILABLE);
    }

    // Checking if schedule available as per requested station and slot
    List<Schedule> scheduleAsPerRequest =
        validSchedules.stream()
            .filter(
                s -> dStationE == null || s.getDStationLabel().equals(dStationE.getStationLabel()))
            .filter(s -> dSlot == null || s.getDSlotName() == dSlot.getName())
            .collect(toList());
    if (scheduleAsPerRequest == null || scheduleAsPerRequest.isEmpty()) {
      if (!scheduleRandomly) {
        throw new RuntimeException(NO_DIALYSIS_SCHEDULE_AVAILABLE_PER_REQUESTED_STATION_AND_SLOT);
      } else {
        return createAndSaveSchedule(patientE, scheduleDate, validSchedules.get(0));
      }
    } else {
      return createAndSaveSchedule(patientE, scheduleDate, scheduleAsPerRequest.get(0));
    }
  }

  private DialysisScheduleE createAndSaveSchedule(
      PatientE patientE, LocalDate scheduleDate, Schedule scheduleToBeBooked) {
    DialysisScheduleE dialysisScheduleE = new DialysisScheduleE();
    dialysisScheduleE.setScheduleDate(scheduleDate);
    dialysisScheduleE.setStatus(SCHEDULED);
    dialysisScheduleE.setDialysisStationE(
        dialysisStationR.findByStationLabel(scheduleToBeBooked.getDStationLabel()).get());
    dialysisScheduleE.setDialysisSlotE(
        dialysisSlotR.findByName(scheduleToBeBooked.getDSlotName()).get());
    dialysisScheduleE.setPatientE(patientE);

    dialysisScheduleR.save(dialysisScheduleE);

    return dialysisScheduleE;
  }

  /**
   * Return possible and valid schedules as per date for booking
   *
   * @param date
   * @return
   */
  public List<Schedule> availableSchedulesForDate(LocalDate date) {
    // List all schedules which are still available on that date
    // eg if slot to be booked for today and booking date/time today/14:00 then slot#1 is not
    // applicable but slot2 (and
    // further slots) are still possible
    List<Schedule> possibleDialysisSchedulesForDate =
        date.isEqual(now())
            ? possibleDialysisSchedulesForDate().stream()
                .filter(
                    schedule -> {
                      return dialysisSlotR
                          .findByName(schedule.getDSlotName())
                          .get()
                          .getEndTime()
                          .isAfter(LocalTime.now().plusHours(DELAY_ACCEPTED_IN_HRS));
                    })
                .collect(toList())
            : possibleDialysisSchedulesForDate();

    List<DialysisScheduleE> filledSchedules = filledDialysisSchedulesForDate(date);

    return possibleDialysisSchedulesForDate.stream()
        .filter(schedule -> !isScheduleAlreadyBooked(schedule, filledSchedules))
        .collect(toList());
  }

  private boolean isScheduleAlreadyBooked(
      Schedule schedule, List<DialysisScheduleE> filledSchedules) {
    return filledSchedules.stream()
        .anyMatch(
            s ->
                s.getDialysisStationE().getStationLabel().equals(schedule.getDStationLabel())
                    && s.getDialysisSlotE().getName() == schedule.getDSlotName()
                    && (s.getStatus() == SCHEDULED || s.getStatus() == COMPLETED));
  }

  /**
   * Return all schedules possible for one day. out of these schedules few might already be booked
   * or gone.
   *
   * @return List<Schedule>
   */
  public List<Schedule> possibleDialysisSchedulesForDate() {
    return dialysisStationR.findByDialysisStationStatus(ACTIVE).stream()
        .map(
            station -> {
              return dialysisSlotR.findAll().stream()
                  .map(slot -> new Schedule(station.getStationLabel(), slot.getName(), AVAILABLE))
                  .collect(toList());
            })
        .flatMap(List::stream)
        .collect(toList());
  }

  /**
   * return already booked schedules for particular date irrespective of status which could be
   * CANCELLED, SCHEDULED or COMPLETE
   *
   * @param date
   * @return
   */
  public List<DialysisScheduleE> filledDialysisSchedulesForDate(LocalDate date) {
    return dialysisScheduleR.findByScheduleDate(date);
  }

  /**
   * Update Dialysis Status to Cancelled or Completed (other status not supported)
   * 
   * TODO:
   *   Can't be COMPLETED more than 1 hr in advance as per slot's End Time
   *   Requested Status must be valid as status's life cycle
   *
   * @param dialysisScheduleId
   * @param dialysisStatusUpdateRequestDto
   */
  public void updateDialysisStatus(
      Long dialysisScheduleId, DialysisStatusUpdateRequestDto dialysisStatusUpdateRequestDto) {
    DialysisScheduleE scheduleE =
        dialysisScheduleR
            .findById(dialysisScheduleId)
            .orElseThrow(() -> new RuntimeException(DIALYSIS_SCHEDULE_NOT_FOUND));

    scheduleE.setDoctorName(dialysisStatusUpdateRequestDto.getDoctorName());
    scheduleE.setNursingStaff(dialysisStatusUpdateRequestDto.getNursingStaff());
    scheduleE.setStatus(
        getEnumIgnoreCase(ScheduleStatus.class, dialysisStatusUpdateRequestDto.getStatus()));

    dialysisScheduleR.save(scheduleE);
  }
}
