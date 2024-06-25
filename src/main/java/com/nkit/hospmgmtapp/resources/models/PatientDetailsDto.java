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
  @JsonProperty("totalBalance")
  private float totalBalance;

  @JsonProperty("billingDetails")
  private List<BillingDetailsDto> billingDetails = new ArrayList<>();

  @JsonProperty("paymentDetails")
  private List<PaymentDto> paymentDetails = new ArrayList<>();

  public PatientDetailsDto(PatientE patientE) {
    super(patientE);
  }
}
