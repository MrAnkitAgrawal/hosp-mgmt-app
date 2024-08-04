package com.nkit.hospmgmtapp.resources.models;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nkit.hospmgmtapp.domain.entities.BillingE;
import java.time.LocalDateTime;
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
@JsonPropertyOrder(
    value = {
      "billingId",
      "billingTimestamp",
      "billingHead",
      "billingRemarks",
      "totalBillAmount",
      "paidAmount",
      "billStatus",
      "billItems"
    })
public class BillingDetailsDto extends BillingDto {
  @JsonProperty("billingId")
  private Long billingId;

  @JsonProperty("billingTimestamp")
  private LocalDateTime billingTimestamp;

  @JsonProperty("billStatus")
  private String billStatus;

  @JsonProperty("payments")
  private List<PaymentDto> payments = new ArrayList<>();

  public BillingDetailsDto(BillingE billingE) {
    super(billingE);
    this.billingId = billingE.getBillingId();
    this.billingTimestamp = billingE.getBillingTimestamp();
    this.billStatus = billingE.getBillStatus().name();
    this.payments = billingE.getBillPayments().stream().map(PaymentDto::new).collect(toList());
  }
}
