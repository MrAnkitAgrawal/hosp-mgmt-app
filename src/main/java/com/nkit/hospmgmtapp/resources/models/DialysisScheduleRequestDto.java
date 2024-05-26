package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.DIALYSIS_SCHEDULE_DATE_NOT_PROVIDED;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.PATIENT_ID_NOT_PROVIDED;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisScheduleRequestDto {
  @NotNull(message = PATIENT_ID_NOT_PROVIDED)
  @JsonProperty("patientId")
  private Long patientId;

  // must be valid, future date. In dd-mmm-yyyy format
  @NotBlank(message = DIALYSIS_SCHEDULE_DATE_NOT_PROVIDED)
  @JsonProperty("dialysisDate")
  private String dialysisDate;

  // if provided then schedule on this station
  @JsonProperty("dialysisStationLabel")
  private String dialysisStationLabel;

  // if provided then schedule as per slot
  @JsonProperty("dialysisSlot")
  private String dialysisSlot;

  @JsonProperty("scheduleRandomlyIfMentionedStationAndSlotNotAvailable")
  private boolean scheduleRandomly;
}
