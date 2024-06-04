package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.InsuranceE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InsuranceDto {
  @JsonProperty("insuranceCompany")
  private String insuranceCompany;

  @JsonProperty("policyNumber")
  private String policyNumber;

  public InsuranceDto(InsuranceE insuranceE) {
    this.insuranceCompany = insuranceE.getInsuranceCompany();
    this.policyNumber = insuranceE.getPolicyNumber();
  }
}
