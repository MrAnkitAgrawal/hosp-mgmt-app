package com.nkit.hospmgmtapp.services.serviceextns;

import static com.nkit.hospmgmtapp.domain.entities.DialysisStationStatus.ACTIVE;
import static com.nkit.hospmgmtapp.domain.entities.ScheduleStatus.*;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toSet;

import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import com.nkit.hospmgmtapp.domain.repos.DialysisScheduleR;
import com.nkit.hospmgmtapp.domain.repos.DialysisSlotR;
import com.nkit.hospmgmtapp.domain.repos.DialysisStationR;
import com.nkit.hospmgmtapp.services.models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialysisScheduleServiceExtn {
  private final DialysisStationR dialysisStationR;
  private final DialysisSlotR dialysisSlotR;
  private final DialysisScheduleR dialysisScheduleR;

  public Set<Appointment> possibleDialysisSchedulesForDate() {
    return dialysisStationR.findByDialysisStationStatus(ACTIVE).stream()
        .map(
            s -> {
              return dialysisSlotR.findAll().stream()
                  .map(slot -> new Appointment(s.getStationLabel(), slot.getName(), AVAILABLE))
                  .collect(toSet());
            })
        .flatMap(Set::stream)
        .collect(toSet());
  }

  public List<DialysisScheduleE> filledDialysisSchedulesForDate(LocalDate date) {
    return dialysisScheduleR.findByScheduleDate(date);
  }

  public Set<Appointment> availableSchedulesForDate(LocalDate date) {
    Set<Appointment> possibleDialysisSchedulesForDate =
        date.isEqual(now())
            ? possibleDialysisSchedulesForDate().stream()
                .filter(
                    a -> {
                      return !(dialysisSlotR
                          .findByName(a.getDSlotName())
                          .get()
                          .getEndTime()
                          .isAfter(LocalTime.now().plusHours(2)));
                    })
                .collect(toSet())
            : possibleDialysisSchedulesForDate();

    List<DialysisScheduleE> filledSchedules = filledDialysisSchedulesForDate(date);

    return possibleDialysisSchedulesForDate.stream()
        .filter(s -> !isScheduleAlreadyBooked(s, filledSchedules))
        .collect(toSet());
  }

  private boolean isScheduleAlreadyBooked(Appointment a, List<DialysisScheduleE> filledSchedules) {
    return filledSchedules.stream()
        .anyMatch(
            s ->
                s.getDialysisStationE().getStationLabel().equals(a.getDStationLabel())
                    && s.getDialysisSlotE().getName() == a.getDSlotName()
                    && (s.getStatus() == SCHEDULED || s.getStatus() == COMPLETED));
  }

  public Appointment randomAvailableSchedule(LocalDate date) {
    Set<Appointment> availableSchedules = availableSchedulesForDate(date);
    return (availableSchedules == null || availableSchedules.isEmpty())
        ? null
        : availableSchedules.stream().findFirst().get();
  }
}
