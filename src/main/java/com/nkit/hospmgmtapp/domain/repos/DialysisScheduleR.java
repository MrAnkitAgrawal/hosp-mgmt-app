package com.nkit.hospmgmtapp.domain.repos;

import com.nkit.hospmgmtapp.domain.entities.DialysisScheduleE;
import com.nkit.hospmgmtapp.domain.entities.ScheduleStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialysisScheduleR extends JpaRepository<DialysisScheduleE, Long> {
  Optional<DialysisScheduleE> findByScheduleDateAndStatusAndPatientEPatientId(
      LocalDate scheduleDate, ScheduleStatus status, Long patientId);

  List<DialysisScheduleE> findByScheduleDate(LocalDate scheduleDate);
}
