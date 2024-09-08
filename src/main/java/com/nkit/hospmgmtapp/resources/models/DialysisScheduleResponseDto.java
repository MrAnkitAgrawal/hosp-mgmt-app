package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.formatName;
import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseDateToString;

import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisScheduleResponseDto {
  private Long scheduleID;
  private Long patientId;
  private String patientName;
  private String patientMobileNumber;
  private String scheduleDate;
  private String dialysisStation;
  private String dialysisSlot;
  private String scheduleStatus;

  public DialysisScheduleResponseDto(DialysisScheduleE scheduleE) {
    this.scheduleID = scheduleE.getDScheduleId();
    this.patientId = scheduleE.getPatientE().getPatientId();
    this.patientName =
        formatName(
            scheduleE.getPatientE().getFirstName(),
            scheduleE.getPatientE().getMiddleName(),
            scheduleE.getPatientE().getLastName());
    this.patientMobileNumber = scheduleE.getPatientE().getOtherMobileNumber();
    this.scheduleDate = parseDateToString(scheduleE.getScheduleDate());
    this.dialysisStation = scheduleE.getDialysisStationE().getStationLabel();
    this.dialysisSlot = scheduleE.getDialysisSlotE().getName().name();
    this.scheduleStatus = scheduleE.getStatus().name();
  }
}
