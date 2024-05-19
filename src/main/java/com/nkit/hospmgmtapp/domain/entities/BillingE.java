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

  @Column(name = "billing_timestamp", nullable = false)
  private LocalDateTime billingTimestamp;

  @OneToMany(mappedBy = "billing", fetch = LAZY)
  private List<BillItemE> billItems = new ArrayList<>();

  @Column(name = "billing_remarks")
  private float billingRemarks;

  @ManyToOne
  @JoinColumn(
      name = "patient_id",
      referencedColumnName = "patient_id",
      updatable = false,
      nullable = false)
  private PatientE patientE;

  @OneToOne(mappedBy = "billReference")
  private PaymentE payment;
}
