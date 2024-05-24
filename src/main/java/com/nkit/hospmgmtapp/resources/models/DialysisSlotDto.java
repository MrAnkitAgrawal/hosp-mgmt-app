package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.DialysisSlot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisSlotDto {
  @JsonProperty("dialysisSlotId")
  private Long id;

  @JsonProperty("dialysisSlotName")
  @NotBlank(message = DIALYSIS_SLOT_NAME_NOT_PROVIDED)
  // @EnumValidate(regexp = "", message = DIALYSIS_SLOT_NAME_INVALID)
  private DialysisSlot name;

  @JsonProperty("dialysisSlotStartTime")
  @NotBlank(message = DIALYSIS_SLOT_START_TIME_NOT_PROVIDED)
  // @Pattern(regexp = "", message = DIALYSIS_SLOT_START_TIME_INVALID)
  private LocalTime startTime;
}
