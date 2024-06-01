package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.PatientE;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientDetailsDto extends PatientDto {
  @JsonProperty("billingDetails")
  private List<BillingDetailsDto> billingDetails = new ArrayList<>();

  public PatientDetailsDto(PatientE patientE) {
    super(patientE);
  }
}
