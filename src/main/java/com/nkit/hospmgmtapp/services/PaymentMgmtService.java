package com.nkit.hospmgmtapp.services;

import com.nkit.hospmgmtapp.domain.entities.PaymentE;
import com.nkit.hospmgmtapp.domain.repos.PaymentR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMgmtService {
  private final PaymentR paymentR;

  public void savePaymentDetails(PaymentE paymentE) {
    paymentR.save(paymentE);
  }
}
