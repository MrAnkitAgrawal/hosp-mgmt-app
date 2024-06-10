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
  @JsonProperty("itemId")
  private Long itemId;

  @JsonProperty("billItemType")
  private String billItemType;

  @JsonProperty("billItemName")
  private String billItemName;

  @JsonProperty("itemQuantity")
  private Integer itemQuantity;

  @JsonProperty("amount")
  private Float amount;

  public BillItemDto(BillItemE billItemE) {
    this.itemId = billItemE.getItemId();
    this.billItemType = billItemE.getBillItemType();
    this.billItemName = billItemE.getBillItemName();
    this.itemQuantity = billItemE.getItemQuantity();
    this.amount = billItemE.getAmount();
  }
}
