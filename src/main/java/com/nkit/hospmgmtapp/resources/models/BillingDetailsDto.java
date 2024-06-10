package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nkit.hospmgmtapp.domain.entities.BillingE;
import java.time.LocalDateTime;
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

  public BillingDetailsDto(BillingE billingE) {
    super(billingE);
    this.billingId = billingE.getBillingId();
    this.billingTimestamp = billingE.getBillingTimestamp();
    this.billStatus = billingE.getBillStatus().name();
  }
}
