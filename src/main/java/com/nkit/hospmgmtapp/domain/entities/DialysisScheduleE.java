package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "DIALYSIS_SCHEDULE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisScheduleE implements Serializable {
  @Serial private static final long serialVersionUID = 2514648103782028333L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_schedule_id_seq_gen")
  @SequenceGenerator(
      name = "d_schedule_id_seq_gen",
      sequenceName = "d_schedule_id_seq_gen",
      initialValue = 1)
  @Column(name = "d_schedule_id", nullable = false, updatable = false)
  private Long dScheduleId;

  @Column(name = "d_schedule_date", nullable = false)
  private LocalDate scheduleDate;

  @Column(name = "d_schedule_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ScheduleStatus status;

  @ManyToOne
  @JoinColumn(
      name = "d_station_id",
      referencedColumnName = "d_station_id",
      nullable = false,
      updatable = false)
  private DialysisStationE dialysisStationE;

  @ManyToOne
  @JoinColumn(
      name = "d_slot_id",
      referencedColumnName = "d_slot_id",
      nullable = false,
      updatable = false)
  private DialysisSlotE dialysisSlotE;

  @ManyToOne
  @JoinColumn(
      name = "patient_id",
      referencedColumnName = "patient_id",
      nullable = false,
      updatable = false)
  private PatientE patientE;

  @CreationTimestamp
  @Column(name = "create_timestamp", nullable = false, updatable = false)
  private LocalDateTime createTimestamp;

  @UpdateTimestamp
  @Column(name = "update_timestamp", nullable = false)
  private LocalDateTime lastUpdateTimestamp;
}
