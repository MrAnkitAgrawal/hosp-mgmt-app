package com.nkit.hospmgmtapp.domain.entities;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class DialysisSlotE implements Serializable {
  @Serial private static final long serialVersionUID = 8978969824983629775L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "d_slot_seq_gen")
  @SequenceGenerator(name = "d_slot_seq_gen", sequenceName = "d_slot_seq_gen", initialValue = 1)
  @Column(name = "d_slot_id", nullable = false, updatable = false)
  private Long id;

  @Column(name = "d_slot_name", nullable = false)
  private DialysisSlot name;

  @Column(name = "d_slot_start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "d_slot_end_time", nullable = false)
  private LocalDateTime endTime;

  @OneToMany(mappedBy = "dialysisSlotE", fetch = LAZY)
  private List<DialysisScheduleE> dialysisSchedules = new ArrayList<>();
}
