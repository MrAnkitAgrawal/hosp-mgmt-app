package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.domain.entities.ScheduleStatus.SCHEDULED;
import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;

import com.nkit.hospmgmtapp.domain.entities.*;
import com.nkit.hospmgmtapp.domain.repos.DialysisScheduleR;
import com.nkit.hospmgmtapp.domain.repos.DialysisSlotR;
import com.nkit.hospmgmtapp.domain.repos.DialysisStationR;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleDto;
import com.nkit.hospmgmtapp.services.models.Appointment;
import com.nkit.hospmgmtapp.services.serviceextns.DialysisScheduleServiceExtn;
import com.nkit.hospmgmtapp.services.validator.DialysisServiceValidator;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialysisScheduleService {
  private final DialysisScheduleServiceExtn dialysisScheduleServiceExtn;
  private final DialysisServiceValidator dialysisServiceValidator;
  private final DialysisStationR dialysisStationR;
  private final DialysisSlotR dialysisSlotR;
  private final DialysisScheduleR dialysisScheduleR;

  public void scheduleDialysis(DialysisScheduleDto dialysisScheduleDto) {
    // Validate patient id
    Long patientId = dialysisScheduleDto.getPatientId();
    PatientE patientE = dialysisServiceValidator.validatePatientId(patientId);

    LocalDate scheduleDate = parseStringToDate(dialysisScheduleDto.getDialysisDate());

    // validate if dialysis already scheduled for this patient on requested date
    dialysisServiceValidator.checkIfDialysisAlreadyScheduled(patientE, scheduleDate);

    // validate dialysis station
    final String dStationLabel = dialysisScheduleDto.getDialysisStationLabel();
    DialysisStationE dStationE = dialysisServiceValidator.validateDialysisStation(dStationLabel);

    // validate dialysis slot
    final String dSlotName = dialysisScheduleDto.getDialysisSlot();
    DialysisSlotE dSlot = dialysisServiceValidator.validateDialysisSlot(dSlotName);

    Appointment schedule = dialysisScheduleServiceExtn.randomAvailableSchedule(scheduleDate);

    DialysisScheduleE dialysisScheduleE = new DialysisScheduleE();
    dialysisScheduleE.setScheduleDate(scheduleDate);
    dialysisScheduleE.setStatus(SCHEDULED);
    dialysisScheduleE.setDialysisStationE(
        dialysisStationR.findByStationLabel(schedule.getDStationLabel()).get());
    dialysisScheduleE.setDialysisSlotE(dialysisSlotR.findByName(schedule.getDSlotName()).get());
    dialysisScheduleE.setPatientE(patientE);

    dialysisScheduleR.save(dialysisScheduleE);
  }
}
