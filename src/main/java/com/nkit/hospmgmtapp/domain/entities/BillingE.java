package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Billing")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BillingE implements Serializable {
  @Serial private static final long serialVersionUID = 7316969804553613076L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_id_seq_gen")
  @SequenceGenerator(
      name = "billing_id_seq_gen",
      sequenceName = "billing_id_seq_gen",
      initialValue = 1)
  @Column(name = "billing_id")
  private Long billingId;

  @Column(name = "billing_head", nullable = false)
  private String billingHead;

  @Column(name = "amount", nullable = false)
  private float amount;

  @Column(name = "billing_timestamp", nullable = false)
  private LocalDateTime billingTimestamp;

  @Column(name = "billing_reference")
  private String billingReference;

  @Column(name = "billing_remarks")
  private float billingRemarks;

  @ManyToOne
  @JoinColumn(
      name = "patient_id",
      referencedColumnName = "patient_id",
      updatable = false,
      nullable = false)
  private PatientE patientE;
}
