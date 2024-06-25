package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.PaymentE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentDto {
  @JsonProperty("paymentId")
  private Long paymentId;

  @JsonProperty("paymentType")
  private String paymentType;

  @JsonProperty("paidAmount")
  private float amount;

  @JsonProperty("paymentMode")
  private String paymentMode;

  @JsonProperty("paymentRemarks")
  private String paymentRemarks;

  public PaymentDto(PaymentE paymentE) {
    this.paymentId = paymentE.getPaymentId();
    this.paymentType = paymentE.getPaymentType() != null ? paymentE.getPaymentType().name() : null;
    this.amount = paymentE.getAmount();
    this.paymentMode = paymentE.getPaymentMode() != null ? paymentE.getPaymentMode().name() : null;
    this.paymentRemarks = paymentE.getPaymentRemarks();
  }
}
