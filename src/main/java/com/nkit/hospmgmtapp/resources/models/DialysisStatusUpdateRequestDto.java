package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DialysisStatusUpdateRequestDto {
  // TODO must be valid as per enum;; support just Cancelled and Completed;;
  @NotBlank(message = "DIALYSIS_SCHEDULE_STATUS_NOT_PROVIDED")
  @JsonProperty("dialysisStatus")
  private String status;

  @JsonProperty("doctorName")
  private String doctorName;

  @JsonProperty("nursingStaffName")
  private String nursingStaff;
}
