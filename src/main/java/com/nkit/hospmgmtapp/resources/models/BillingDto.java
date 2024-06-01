package com.nkit.hospmgmtapp.resources.models;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nkit.hospmgmtapp.domain.entities.BillingE;
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
public class BillingDto {
  @JsonProperty("billingHead")
  private String billingHead;

  @JsonProperty("billingRemarks")
  private String billingRemarks;

  @JsonProperty("billItems")
  private List<BillItemDto> billItems = new ArrayList<>();

  public BillingDto(BillingE billingE) {
    this.billingHead = billingE.getBillingHead();
    this.billingRemarks = billingE.getBillingRemarks();
    billItems.addAll(billingE.getBillItems().stream().map(BillItemDto::new).collect(toList()));
  }
}
