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
@Table(name = "PAYMENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentE implements Serializable {
  @Serial private static final long serialVersionUID = -2335790453176498830L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq_gen")
  @SequenceGenerator(
      name = "payment_id_seq_gen",
      sequenceName = "payment_id_seq_gen",
      initialValue = 1)
  @Column(name = "payment_id", nullable = false, updatable = false)
  private Long paymentId;

  @Column(name = "payment_timestamp", nullable = false)
  private LocalDateTime paymentTimestamp;

  @Column(name = "paid_amount", nullable = false)
  private float paidAmount;

  @Column(name = "payment_mode", nullable = false)
  private PaymentMode paymentMode;

  @Column(name = "payment_mode_reference")
  private PaymentMode paymentModeReference;

  @Column(name = "payment_remark")
  private String paymentRemarks;

  @ManyToOne
  @JoinColumn(
      name = "patient_id",
      referencedColumnName = "patient_id",
      updatable = false,
      nullable = false)
  private PatientE patientE;

  @OneToOne
  @JoinColumn(name = "billing_id", referencedColumnName = "billing_id", nullable = false)
  private BillingE billReference;
}
