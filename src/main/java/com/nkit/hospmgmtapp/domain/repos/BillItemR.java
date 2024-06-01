package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.BillItemE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemR extends JpaRepository<BillItemE, Long> {}
