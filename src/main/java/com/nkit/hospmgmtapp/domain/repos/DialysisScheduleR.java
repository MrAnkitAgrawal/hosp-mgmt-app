package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialysisScheduleR extends JpaRepository<DialysisScheduleE, Long> {
  Optional<DialysisScheduleE> findByScheduleDateAndPatientEPatientId(
      LocalDate scheduleDate, Long patientId);

  List<DialysisScheduleE> findByScheduleDate(LocalDate scheduleDate);
}
