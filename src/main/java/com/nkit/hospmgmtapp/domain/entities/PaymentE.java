package com.nkit.hospmgmtapp.domain.entities;

import com.nkit.hospmgmtapp.resources.models.PaymentDto;
import com.nkit.hospmgmtapp.resources.models.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

@Entity
@Table(name = "PAYMENT")
@Getter
@Setter
@ToString(exclude = {"billings", "patientE"})
@NoArgsConstructor
public class PaymentE implements Serializable {
  @Serial private static final long serialVersionUID = -2335790453176498830L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq_gen")
  @SequenceGenerator(
      name = "payment_id_seq_gen",
      sequenceName = "payment_id_seq_gen",
      initialValue = 1,
      allocationSize = 1)
  @Column(name = "payment_id", nullable = false, updatable = false)
  private Long paymentId;

  @Column(name = "payment_type", nullable = false, updatable = false)
  @Enumerated(STRING)
  private PaymentType paymentType;

  @Column(name = "payment_timestamp", nullable = false)
  @UpdateTimestamp
  private LocalDateTime paymentTimestamp;

  @Column(name = "amount", nullable = false)
  private float amount;

  @Column(name = "payment_mode", nullable = false)
  @Enumerated(STRING)
  private PaymentMode paymentMode;

  @Column(name = "payment_remark")
  private String paymentRemarks;

  @ManyToMany(mappedBy = "billPayments")
  private List<BillingE> billings = new ArrayList<>();

  @ManyToOne
  @JoinColumn(
      name = "patient_id",
      referencedColumnName = "patient_id",
      updatable = false,
      nullable = false)
  private PatientE patientE;

  public PaymentE(PaymentDto paymentDto) {
    this.paymentId = paymentDto.getPaymentId();
    this.paymentType = getEnumIgnoreCase(PaymentType.class, paymentDto.getPaymentType());
    this.amount = paymentDto.getAmount();
    this.paymentMode = getEnumIgnoreCase(PaymentMode.class, paymentDto.getPaymentMode());
    this.paymentRemarks = paymentDto.getPaymentRemarks();
  }
}
