package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.domain.entities.BillStatus.*;
import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;
import static java.util.stream.Collectors.toList;

import com.nkit.hospmgmtapp.domain.entities.BillingE;
import com.nkit.hospmgmtapp.domain.entities.PatientE;
import com.nkit.hospmgmtapp.domain.entities.PaymentE;
import com.nkit.hospmgmtapp.domain.repos.BillingR;
import com.nkit.hospmgmtapp.domain.repos.InsuranceR;
import com.nkit.hospmgmtapp.domain.repos.PatientR;
import com.nkit.hospmgmtapp.domain.repos.PaymentR;
import com.nkit.hospmgmtapp.resources.models.BillingDetailsDto;
import com.nkit.hospmgmtapp.resources.models.PatientDetailsDto;
import com.nkit.hospmgmtapp.resources.models.PatientDto;
import com.nkit.hospmgmtapp.resources.models.PaymentDto;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientR patientR;
  private final InsuranceR insuranceR;
  private final PaymentR paymentR;
  private final BillingR billingR;

  public Long createPatient(PatientDto patientDto) {
    PatientE patientE = new PatientE(patientDto);
    patientE.getInsurances().forEach(i -> i.setPatientE(patientE));

    Long patientId = patientR.save(patientE).getPatientId();
    insuranceR.saveAll(patientE.getInsurances());

    return patientId;
  }

  public PatientDto retrievePatientById(Long patientId) {
    return new PatientDto(getPatientE(patientId));
  }

  public List<PatientDto> retrieveAllPatient() {
    return patientR.findAll().stream().map(PatientDto::new).collect(toList());
  }

  public void updatePatient(Long patientId, PatientDto patientDto) {
    PatientE existingPatientE = getPatientE(patientId);
    existingPatientE.getInsurances().forEach(insuranceR::delete);

    PatientE updatedPatientE = new PatientE(patientDto);
    updatedPatientE.setPatientId(existingPatientE.getPatientId());
    updatedPatientE.getInsurances().forEach(i -> i.setPatientE(updatedPatientE));

    patientR.save(updatedPatientE);
    insuranceR.saveAll(updatedPatientE.getInsurances());
  }

  public void removePatient(Long patientId) {
    PatientE patientE = getPatientE(patientId);
    patientE.getInsurances().forEach(insuranceR::delete);
    patientR.delete(patientE);
  }

  public PatientDto retrievePatientBillingDetailsById(Long patientId) {
    PatientE patientE = getPatientE(patientId);
    PatientDetailsDto patientDetailsDto = new PatientDetailsDto(patientE);

    // retrieving last 5 bills
    List<BillingDetailsDto> recentBills =
        billingR.findFiveByPatientEPatientIdOrderByBillingIdDesc(patientE.getPatientId()).stream()
            .map(BillingDetailsDto::new)
            .collect(toList());
    patientDetailsDto.setBillingDetails(recentBills);

    // calculate total pending bills for the patient
    List<BillingE> pendingBills =
        billingR.findByBillStatusInAndPatientEPatientId(
            Arrays.asList(DUE, PARTIALLY_PAID), patientE.getPatientId());
    float pendingBillsAmount = 0;
    if (pendingBills != null && !pendingBills.isEmpty()) {
      pendingBillsAmount =
          pendingBills.stream()
              .map(billingE -> billingE.getTotalBill() - billingE.getPaidAmount())
              .reduce(Float::sum)
              .get();
    }
    patientDetailsDto.setTotalBalance(pendingBillsAmount);

    return patientDetailsDto;
  }

  @Transactional
  public void addPatientPaymentDetails(Long patientId, PaymentDto paymentDto) {
    PatientE patientE = getPatientE(patientId);
    List<PaymentE> existingPayments = patientE.getPayments();

    // Add payment details
    paymentDto.setPaymentId(null);
    PaymentE newPayment = new PaymentE(paymentDto);
    newPayment.setPatientE(patientE);
    existingPayments.add(newPayment);

    paymentR.save(newPayment);
    patientR.save(patientE);

    List<BillingE> billsToBeProcessed = paymentDto.getBillingDetails().stream().map(billingDto -> getBillingE(billingDto.getBillingId())).collect(toList());

    // check all due bills and settle one by one as per paid amount
    AtomicReference<Float> paidAmount = new AtomicReference<>(newPayment.getAmount());

    billsToBeProcessed.forEach(bill -> {
      if (bill.getBillStatus() != DUE && bill.getBillStatus() != PARTIALLY_PAID) {
        throw new RuntimeException(BILL_NOT_READY_OR_ALREADY_PAID);
      }

      float dueAmount = bill.getTotalBill() - bill.getPaidAmount();

      if (dueAmount > 0 && dueAmount <= paidAmount.get()) {
        bill.setBillStatus(PAID);
        bill.setPaidAmount(bill.getPaidAmount() + dueAmount);
        paidAmount.set(paidAmount.get() - dueAmount);
      } else if (paidAmount.get() > 0 && dueAmount > paidAmount.get()) {
        bill.setBillStatus(PARTIALLY_PAID);
        bill.setPaidAmount(bill.getPaidAmount() + paidAmount.get());
        paidAmount.set(0f);
      }

      bill.getBillPayments().add(newPayment);
      newPayment.getBillings().add(bill);

      billingR.save(bill);
      paymentR.save(newPayment);
    });

    if (paidAmount.get() > 0) {
      throw new RuntimeException(PAID_AMOUNT_IS_MORE_THAN_PENDING_BILLS);
    }
  }

  private PatientE getPatientE(Long patientId) {
    return patientR.findById(patientId).orElseThrow(() -> new RuntimeException(PATIENT_NOT_FOUND));
  }

  private BillingE getBillingE(Long billingId) {
    return billingR.findById(billingId).orElseThrow(() -> new RuntimeException(BILL_NOT_FOUND));
  }
}
