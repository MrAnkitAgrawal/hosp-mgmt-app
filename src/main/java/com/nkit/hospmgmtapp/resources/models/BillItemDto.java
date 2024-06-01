package com.nkit.hospmgmtapp.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.BillItemE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BillItemDto {
  @JsonProperty("billItem")
  private String billItem;

  @JsonProperty("amount")
  private float amount;

  public BillItemDto(BillItemE billItemE) {
    this.billItem = billItemE.getBillItem();
    this.amount = billItemE.getAmount();
  }
}
