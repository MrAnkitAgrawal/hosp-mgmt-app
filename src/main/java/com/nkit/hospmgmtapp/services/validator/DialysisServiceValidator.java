package com.nkit.hospmgmtapp.services.validator;

import static com.nkit.hospmgmtapp.domain.entities.DialysisStationStatus.ACTIVE;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;
import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.nkit.hospmgmtapp.domain.entities.DialysisSlot;
import com.nkit.hospmgmtapp.domain.entities.DialysisSlotE;
import com.nkit.hospmgmtapp.domain.entities.DialysisStationE;
import com.nkit.hospmgmtapp.domain.entities.PatientE;
import com.nkit.hospmgmtapp.domain.repos.DialysisScheduleR;
import com.nkit.hospmgmtapp.domain.repos.DialysisSlotR;
import com.nkit.hospmgmtapp.domain.repos.DialysisStationR;
import com.nkit.hospmgmtapp.domain.repos.PatientR;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialysisServiceValidator {
  private final PatientR patientR;
  private final DialysisScheduleR dialysisScheduleR;
  private final DialysisStationR dialysisStationR;
  private final DialysisSlotR dialysisSlotR;

  public PatientE validatePatientId(Long patientId) {
    return patientR.findById(patientId).orElseThrow(() -> new RuntimeException(PATIENT_ID_INVALID));
  }

  public void checkIfDialysisAlreadyScheduled(PatientE patientE, LocalDate scheduleDate) {
    dialysisScheduleR
        .findByScheduleDateAndPatientEPatientId(scheduleDate, patientE.getPatientId())
        .ifPresent(
            schedule ->
                new RuntimeException(
                    DIALYSIS_ALREADY_SCHEDULED_FOR_THIS_PATIENT_ON_REQUESTED_DATE));
  }

  public DialysisStationE validateDialysisStation(String dialysisStationLabel) {
    if (isBlank(dialysisStationLabel)) {
      return null;
    }

    DialysisStationE dialysisStationE =
        dialysisStationR
            .findByStationLabel(dialysisStationLabel)
            .orElseThrow(() -> new RuntimeException(DIALYSIS_STATION_INVALID));

    if (dialysisStationE.getDialysisStationStatus() != ACTIVE) {
      throw new RuntimeException(DIALYSIS_STATION_IS_NOT_AVAILABLE_FOR_DIALYSIS);
    }

    return dialysisStationE;
  }

  public DialysisSlotE validateDialysisSlot(String dialysisSlot) {
    if (isBlank(dialysisSlot)) {
      return null;
    }

    return dialysisSlotR
        .findByName(getEnumIgnoreCase(DialysisSlot.class, dialysisSlot))
        .orElseThrow(() -> new RuntimeException(DIALYSIS_SLOT_NAME_INVALID));
  }
}
