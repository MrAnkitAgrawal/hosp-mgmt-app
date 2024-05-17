package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DIALYSIS_STATION")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisStationE {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_station_seq_gen")
  @SequenceGenerator(
      name = "d_station_seq_gen",
      sequenceName = "d_station_seq_gen",
      initialValue = 1)
  @Column(name = "d_station_id")
  private Long id;

  @Column(name = "label", nullable = false)
  private String dStationLabel;

  @Column(name = "bed_number", nullable = false)
  private String bedNumber;

  @Column(name = "dialysis_machine_number", nullable = false)
  private String dialysisMachineNumber;

  @Column(name = "in_use", nullable = false)
  private boolean inUse;

  @Column(name = "last_maintenance_date")
  private LocalDate lastMaintenanceDate;

  @Column(name = "last_maintenance_details")
  private String lastMaintenanceDetails;
}
