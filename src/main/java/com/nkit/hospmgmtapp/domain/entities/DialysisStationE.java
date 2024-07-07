package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "DIALYSIS_STATION")
@Getter
@Setter
@ToString(exclude = "dialysisSchedules")
@NoArgsConstructor
public class DialysisStationE implements Serializable {
  @Serial private static final long serialVersionUID = 853232371219102215L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_station_seq_gen")
  @SequenceGenerator(
      name = "d_station_seq_gen",
      sequenceName = "d_station_seq_gen",
      initialValue = 1)
  @Column(name = "d_station_id")
  private Long id;

  @Column(name = "label", nullable = false)
  private String stationLabel;

  @Column(name = "bed_number", nullable = false)
  private String bedNumber;

  @Column(name = "dialysis_machine_number", nullable = false)
  private String dialysisMachineNumber;

  @Column(name = "dialysis_station_status", nullable = false)
  @Enumerated(STRING)
  private DialysisStationStatus dialysisStationStatus;

  @Column(name = "last_maintenance_date")
  private LocalDate lastMaintenanceDate;

  @Column(name = "last_maintenance_details")
  private String lastMaintenanceDetails;

  @OneToMany(mappedBy = "dialysisStationE", fetch = LAZY)
  private List<DialysisScheduleE> dialysisSchedules = new ArrayList<>();
}
