package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.BillingE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingR extends JpaRepository<BillingE, Long> {}
