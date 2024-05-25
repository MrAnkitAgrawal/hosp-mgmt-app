package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.DialysisSlot;
import com.nkit.hospmgmtapp.domain.entities.DialysisSlotE;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialysisSlotR extends JpaRepository<DialysisSlotE, Long> {
  Optional<DialysisSlotE> findByName(DialysisSlot slotName);
}
