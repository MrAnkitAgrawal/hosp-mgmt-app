package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.BillStatus;
import com.nkit.hospmgmtapp.domain.entities.BillingE;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingR extends JpaRepository<BillingE, Long> {
  List<BillingE> findFiveByPatientEPatientIdOrderByBillingIdDesc(Long patientId);

  List<BillingE> findByBillStatusInAndPatientEPatientId(List<BillStatus> statuses, Long patientId);
}
