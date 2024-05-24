package com.nkit.hospmgmtapp.services.models;

import com.nkit.hospmgmtapp.domain.entities.DialysisSlotE;
import com.nkit.hospmgmtapp.domain.entities.DialysisStationE;
import com.nkit.hospmgmtapp.domain.entities.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Appointment {
  private DialysisStationE dialysisStation;
  private DialysisSlotE dialysisSlot;
  private ScheduleStatus scheduleStatus;
}
