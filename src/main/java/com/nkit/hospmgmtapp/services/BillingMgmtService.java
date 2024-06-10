package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.domain.entities.BillStatus.DUE;
import static com.nkit.hospmgmtapp.domain.entities.BillStatus.OPEN;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;
import static java.util.stream.Collectors.toList;

import com.nkit.hospmgmtapp.domain.entities.BillItemE;
import com.nkit.hospmgmtapp.domain.entities.BillingE;
import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import com.nkit.hospmgmtapp.domain.repos.BillItemR;
import com.nkit.hospmgmtapp.domain.repos.BillingR;
import com.nkit.hospmgmtapp.resources.models.BillingDto;
import com.nkit.hospmgmtapp.services.serviceextns.DialysisScheduleServiceExtn;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
      updateAndSaveBillingDetails(billingDetails, existingBillingE);
    } else {
      BillingE requestedBillingE = new BillingE(billingDetails);
      requestedBillingE.setBillStatus(OPEN);
      requestedBillingE.setDialysisScheduleE(dialysisScheduleE);
      requestedBillingE.setPatientE(dialysisScheduleE.getPatientE());
      requestedBillingE.getBillItems().forEach(item -> item.setBilling(requestedBillingE));

      saveNewBillingDetails(requestedBillingE);
    }
  }

  public BillingDto getDialysisBillingDetails(long dialysisScheduleId) {
    DialysisScheduleE scheduleE = dialysisScheduleServiceExtn.getScheduleEntity(dialysisScheduleId);
    if (scheduleE.getBillingE() == null) {
      throw new RuntimeException(DIALYSIS_BILLING_IS_NOT_AVAILABLE);
    }
    return new BillingDto(scheduleE.getBillingE());
  }

  /**
   * Updating billing and bill items.
   *
   * <p>NOTE: Save child entities first and later parent because. If save parent first then it
   * resets new child's (which not present in DB already) properties to null.
   *
   * @param billingDetails {@link BillingDto}
   * @param existingBillingE {@link BillingE}
   */
  private void updateAndSaveBillingDetails(BillingDto billingDetails, BillingE existingBillingE) {
    List<BillItemE> existingBillItems = existingBillingE.getBillItems();
    List<BillItemE> requestedBillItems =
        billingDetails.getBillItems().stream().map(BillItemE::new).collect(toList());

    requestedBillItems.forEach(
        rItem -> {
          Optional<BillItemE> eBillItemOptional =
              existingBillItems.stream()
                  .filter(eItem -> eItem.getItemId().equals(rItem.getItemId()))
                  .findFirst();
          if (eBillItemOptional.isPresent()) {
            existingBillItems.remove(eBillItemOptional.get());
          } else {
            rItem.setItemId(null);
          }
          rItem.setBilling(existingBillingE);
        });

    // linking existing Billing Entity to requested Bill Item entities
    existingBillingE.setBillItems(requestedBillItems);

    // Persisting changes to DB
    existingBillItems.forEach(billItemR::delete);
    requestedBillItems.forEach(billItemR::save);
    billingR.save(existingBillingE);
  }

  /**
   * Save {@link BillingE} and related {@link BillItemE}. Make sure that both entities are linked
   * with each other and save parent entity first then child.
   *
   * @param billingE {@link BillingE}
   */
  private void saveNewBillingDetails(BillingE billingE) {
    billingR.save(billingE);
    billItemR.saveAll(billingE.getBillItems());
  }

  public void calculateTotalBillAndSetStatusAsDue(BillingE billingE) {
    calculateTotalBill(billingE);
    billingE.setBillStatus(DUE);
    billingR.save(billingE);
  }

  /**
   * Calculate and set total bill amount
   *
   * @param billingE {@link BillingE}
   */
  public void calculateTotalBill(BillingE billingE) {
    billingE.setTotalBill(
        billingE.getBillItems().stream()
            .map(item -> item.getAmount())
            .filter(itemAmount -> itemAmount != null)
            .reduce(Float::sum)
            .get());
  }
}
