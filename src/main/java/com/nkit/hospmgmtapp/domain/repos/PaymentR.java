package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.PaymentE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentR extends JpaRepository<PaymentE, Long> {}
