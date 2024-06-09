package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.domain.entities.BillStatus.OPEN;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.BILL_ITEMS_NOT_PROVIDED;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.BILL_NOT_OPENED_FOR_UPDATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.stream.Collectors.toList;

import com.nkit.hospmgmtapp.domain.entities.BillItemE;
import com.nkit.hospmgmtapp.domain.entities.BillingE;
import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import com.nkit.hospmgmtapp.domain.repos.BillItemR;
import com.nkit.hospmgmtapp.domain.repos.BillingR;
import com.nkit.hospmgmtapp.resources.models.BillingDto;
import com.nkit.hospmgmtapp.services.serviceextns.DialysisScheduleServiceExtn;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillingMgmtService {
  private final DialysisScheduleServiceExtn dialysisScheduleServiceExtn;
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

  @Transactional
  public void addOrUpdateBilling(long dialysisId, BillingDto billingDetails) {
    DialysisScheduleE dialysisScheduleE = dialysisScheduleServiceExtn.getScheduleEntity(dialysisId);
    BillingE existingBillingE = dialysisScheduleE.getBillingE();

    if (existingBillingE != null) {
      if (existingBillingE.getBillStatus() != OPEN) {
        throw new RuntimeException(BILL_NOT_OPENED_FOR_UPDATE);
      }
      existingBillingE.setBillingHead(billingDetails.getBillingHead());
      updateBillingRemarks(existingBillingE, billingDetails);
      updateBillItems(billingDetails, existingBillingE);

      saveBillingDetails(existingBillingE);
    } else {
      BillingE requestedBillingE = new BillingE(billingDetails);
      requestedBillingE.setBillStatus(OPEN);
      requestedBillingE.setBillingRemarks(null);
      updateBillingRemarks(requestedBillingE, billingDetails);
      requestedBillingE.setDialysisScheduleE(dialysisScheduleE);
      requestedBillingE.setPatientE(dialysisScheduleE.getPatientE());
      requestedBillingE.getBillItems().forEach(item -> item.setBilling(requestedBillingE));

      saveBillingDetails(requestedBillingE);
    }
  }

  /**
   * Delete existing billing items and add requested items.
   *
   * @param billingDetails {@link BillingDto}
   * @param existingBillingE {@link BillingE}
   */
  private void updateBillItems(BillingDto billingDetails, BillingE existingBillingE) {
    existingBillingE.getBillItems().forEach(item -> {
      billItemR.delete(item);
    });

    List<BillItemE> requestedBillItems =
        billingDetails.getBillItems().stream().map(BillItemE::new).collect(toList());
    requestedBillItems.forEach(item -> item.setBilling(existingBillingE));
    existingBillingE.setBillItems(requestedBillItems);
  }

  /**
   * Append billing remarks to previous with timestamp
   *
   * @param billingE {@link BillingE}
   * @param billingDetails {@link BillingDto}
   */
  private void updateBillingRemarks(BillingE billingE, BillingDto billingDetails) {
    String remark =
        billingE.getBillingRemarks() == null ? "" : billingE.getBillingRemarks()
            + LocalDateTime.now().format(ISO_DATE_TIME)
            + ": "
            + billingDetails.getBillingRemarks()
            + "\n";
    billingE.setBillingRemarks(remark);
  }

  /**
   * Save {@link BillingE} and related {@link BillItemE}. Make sure that both entities are linked
   * with each other.
   *
   * @param billingE {@link BillingE}
   */
  private void saveBillingDetails(BillingE billingE) {
    billingR.save(billingE);
    billItemR.saveAll(billingE.getBillItems());
  }
}
