package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentDto {
  @JsonProperty("paidAmount")
  private float paidAmount;

  @JsonProperty("paymentMode")
  private String paymentMode;

  @JsonProperty("paymentModeReference")
  private String paymentModeReference;

  @JsonProperty("paymentRemarks")
  private String paymentRemarks;
}
