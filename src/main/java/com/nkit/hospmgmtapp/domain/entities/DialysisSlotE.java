package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DIALYSIS_SLOT")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DialysisSlotE {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_slot_seq_gen")
  @SequenceGenerator(name = "d_slot_seq_gen", sequenceName = "d_slot_seq_gen", initialValue = 1)
  @Column(name = "d_slot_id")
  private Long id;

  @Column(name = "d_slot_name", nullable = false)
  private DialysisSlot name;

  @Column(name = "d_slot_start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "d_slot_end_time", nullable = false)
  private LocalDateTime endTime;
}
