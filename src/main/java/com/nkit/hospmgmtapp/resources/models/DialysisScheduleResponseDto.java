package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseDateToString;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
  private String patientName;
  private String patientMobileNumber;
  private String scheduleDate;
  private String dialysisStation;
  private String dialysisSlot;
  private String scheduleStatus;

  public DialysisScheduleResponseDto(DialysisScheduleE scheduleE) {
    this.scheduleID = scheduleE.getDScheduleId();
    this.patientName =
        scheduleE.getPatientE().getFirstName()
            + (isBlank(scheduleE.getPatientE().getMiddleName())
                ? ""
                : " " + scheduleE.getPatientE().getMiddleName())
            + " "
            + scheduleE.getPatientE().getLastName();
    this.patientMobileNumber = scheduleE.getPatientE().getOtherMobileNumber();
    this.scheduleDate = parseDateToString(scheduleE.getScheduleDate());
    this.dialysisStation = scheduleE.getDialysisStationE().getStationLabel();
    this.dialysisSlot = scheduleE.getDialysisSlotE().getName().name();
    this.scheduleStatus = scheduleE.getStatus().name();
  }
}
