package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.DialysisStationE;
import com.nkit.hospmgmtapp.domain.entities.DialysisStationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialysisStationR extends JpaRepository<DialysisStationE, Long> {
  List<DialysisStationE> findByDialysisStationStatus(DialysisStationStatus dialysisStationStatus);

  Optional<DialysisStationE> findByStationLabel(String stationLabel);
}
