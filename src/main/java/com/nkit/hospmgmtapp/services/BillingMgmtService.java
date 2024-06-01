package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.BILL_ITEMS_NOT_PROVIDED;

import com.nkit.hospmgmtapp.domain.entities.BillingE;
import com.nkit.hospmgmtapp.domain.repos.BillItemR;
import com.nkit.hospmgmtapp.domain.repos.BillingR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillingMgmtService {
  private final BillingR billingR;
  private final BillItemR billItemR;

  public void saveBilling(BillingE billingE) {
    if (billingE.getBillItems() == null || billingE.getBillItems().isEmpty()) {
      throw new RuntimeException(BILL_ITEMS_NOT_PROVIDED);
    }

    billingE.getBillItems().forEach(billItemE -> billItemE.setBilling(billingE));
    billingR.save(billingE);
    billItemR.saveAll(billingE.getBillItems());
  }
}
