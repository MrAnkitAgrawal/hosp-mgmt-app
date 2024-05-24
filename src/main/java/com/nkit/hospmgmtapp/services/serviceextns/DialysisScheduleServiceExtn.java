package com.nkit.hospmgmtapp.services.serviceextns;

import static com.nkit.hospmgmtapp.domain.entities.DialysisStationStatus.ACTIVE;
import static com.nkit.hospmgmtapp.domain.entities.ScheduleStatus.AVAILABLE;
import static java.util.stream.Collectors.toSet;

import com.nkit.hospmgmtapp.domain.repos.DialysisSlotR;
import com.nkit.hospmgmtapp.domain.repos.DialysisStationR;
import com.nkit.hospmgmtapp.services.models.Appointment;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialysisScheduleServiceExtn {
  private final DialysisStationR dialysisStations;
  private final DialysisSlotR dialysisSlots;

  public Set<Appointment> dialysisSchedules() {
    return dialysisStations.findByDialysisStationStatus(ACTIVE).stream()
        .map(
            s -> {
              return dialysisSlots.findAll().stream()
                  .map(slot -> new Appointment(s, slot, AVAILABLE))
                  .collect(toSet());
            })
        .flatMap(Set::stream)
        .collect(toSet());
  }
}
