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

  @JsonProperty("billingDetails")
  private BillingDto billingDetails;

  @JsonProperty("paymentDetails")
  private PaymentDto paymentDto;

  @JsonProperty("nextDialysisDetails")
  private DialysisScheduleRequestDto nextDialysisDetails;
}
