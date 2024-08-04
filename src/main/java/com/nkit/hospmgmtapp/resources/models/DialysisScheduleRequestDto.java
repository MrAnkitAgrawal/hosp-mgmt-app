package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.DialysisSlot;
import com.nkit.hospmgmtapp.domain.entities.DialysisStationLabel;
import com.nkit.hospmgmtapp.validators.annotations.InvalidEnum;
import com.nkit.hospmgmtapp.validators.annotations.NotPastDate;
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
  @NotPastDate(message = DIALYSIS_SCHEDULE_DATE_INVALID)
  @JsonProperty("dialysisDate")
  private String dialysisDate;

  // if provided then schedule on this station
  @JsonProperty("dialysisStationLabel")
  @InvalidEnum(
      targetClassType = DialysisStationLabel.class,
      message = DIALYSIS_STATION_LABEL_INVALID)
  private String dialysisStationLabel;

  // if provided then schedule as per slot
  @JsonProperty("dialysisSlot")
  @InvalidEnum(targetClassType = DialysisSlot.class, message = DIALYSIS_SLOT_INVALID)
  private String dialysisSlot;

  @JsonProperty("scheduleRandomlyIfMentionedStationAndSlotNotAvailable")
  private boolean scheduleRandomly;
}
