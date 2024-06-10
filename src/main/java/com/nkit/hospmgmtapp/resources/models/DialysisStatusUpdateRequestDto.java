package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.DIALYSIS_SCHEDULE_STATUS_CAN_BE_CANCELLED_OR_COMPLETED;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.DIALYSIS_SCHEDULE_STATUS_NOT_PROVIDED;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.ScheduleStatus;
import com.nkit.hospmgmtapp.validators.annotations.ValidDialysisClosureStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
// TODO Doctor and Staff must be provided if dialysis COMPLETED and not allowed in CANCELLED
// TODO Declaration must be true if COMPLETED
public class DialysisStatusUpdateRequestDto {
  @JsonProperty("dialysisStatus")
  @NotNull(message = DIALYSIS_SCHEDULE_STATUS_NOT_PROVIDED)
  @ValidDialysisClosureStatus(
      targetClassType = ScheduleStatus.class,
      message = DIALYSIS_SCHEDULE_STATUS_CAN_BE_CANCELLED_OR_COMPLETED)
  private String status;

  @JsonProperty("doctorName")
  private String doctorName;

  @JsonProperty("nursingStaffName")
  private String nursingStaff;

  @JsonProperty("nextDialysisDetails")
  private DialysisScheduleRequestDto nextDialysisDetails;

  @JsonProperty("declareThatBillCheckedAndVerifiedWithCustomer")
  private boolean declareThatBillCheckedAndVerifiedWithCustomer;
}
