package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;

import com.nkit.hospmgmtapp.domain.entities.*;
import com.nkit.hospmgmtapp.domain.repos.DialysisScheduleR;
import com.nkit.hospmgmtapp.domain.repos.DialysisSlotR;
import com.nkit.hospmgmtapp.domain.repos.DialysisStationR;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleRequestDto;
import com.nkit.hospmgmtapp.resources.models.DialysisScheduleResponseDto;
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

  public DialysisScheduleResponseDto scheduleDialysis(
      DialysisScheduleRequestDto dialysisScheduleRequestDto) {
    // Validate patient id
    Long patientId = dialysisScheduleRequestDto.getPatientId();
    PatientE patientE = dialysisServiceValidator.validatePatientId(patientId);

    LocalDate scheduleDate = parseStringToDate(dialysisScheduleRequestDto.getDialysisDate());

    // validate if dialysis already scheduled for this patient on requested date
    dialysisServiceValidator.checkIfDialysisAlreadyScheduled(patientE, scheduleDate);

    // validate dialysis station
    final String dStationLabel = dialysisScheduleRequestDto.getDialysisStationLabel();
    DialysisStationE dStationE = dialysisServiceValidator.validateDialysisStation(dStationLabel);

    // validate dialysis slot
    final String dSlotName = dialysisScheduleRequestDto.getDialysisSlot();
    DialysisSlotE dSlot = dialysisServiceValidator.validateDialysisSlot(dSlotName);

    DialysisScheduleE scheduleE =
        dialysisScheduleServiceExtn.bookDialysisSchedule(
            patientE,
            scheduleDate,
            dStationE,
            dSlot,
            dialysisScheduleRequestDto.isScheduleRandomly());
    return new DialysisScheduleResponseDto(scheduleE);
  }
}
