package com.nkit.hospmgmtapp.services.models;

import com.nkit.hospmgmtapp.domain.entities.DialysisSlot;
import com.nkit.hospmgmtapp.domain.entities.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
  private String dStationLabel;
  private DialysisSlot dSlotName;
  private ScheduleStatus scheduleStatus;
}
