package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.PatientE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientR extends JpaRepository<PatientE, Long> {}
