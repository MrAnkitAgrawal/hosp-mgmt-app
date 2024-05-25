package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.DIALYSIS_SCHEDULE_DATE_NOT_PROVIDED;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.PATIENT_ID_NOT_PROVIDED;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisScheduleDto {
  @NotNull(message = PATIENT_ID_NOT_PROVIDED)
  private Long patientId;

  // must be valid, future date. In dd-mmm-yyyy format
  @NotBlank(message = DIALYSIS_SCHEDULE_DATE_NOT_PROVIDED)
  private String dialysisDate;

  // if provided then schedule on this station
  private String dialysisStationLabel;

  // if provided then schedule as per slot
  private String dialysisSlot;
}
