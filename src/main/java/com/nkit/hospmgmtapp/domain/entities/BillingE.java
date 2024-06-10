package com.nkit.hospmgmtapp.domain.entities;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.stream.Collectors.toList;

import com.nkit.hospmgmtapp.resources.models.BillingDto;
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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Billing")
@Getter
@Setter
@ToString(exclude = {"billItems", "patientE", "dialysisScheduleE", "payment"})
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
  @UpdateTimestamp
  private LocalDateTime billingTimestamp;

  @Column(name = "billing_remarks")
  private String billingRemarks;

  @Column(name = "bill_status")
  @Enumerated(STRING)
  private BillStatus billStatus;

  @Column(name = "total_bill")
  private Float totalBill;

  @OneToMany(mappedBy = "billing", fetch = LAZY)
  private List<BillItemE> billItems = new ArrayList<>();

  @ManyToOne
  @JoinColumn(
      name = "patient_id",
      referencedColumnName = "patient_id",
      updatable = false,
      nullable = false)
  private PatientE patientE;

  @OneToOne
  @JoinColumn(referencedColumnName = "d_schedule_id", name = "d_schedule_id")
  private DialysisScheduleE dialysisScheduleE;

  @OneToOne(mappedBy = "billReference")
  private PaymentE payment;

  public BillingE(BillingDto billingDto) {
    this.billingHead = billingDto.getBillingHead();
    this.billingRemarks = billingDto.getBillingRemarks();
    this.billItems = billingDto.getBillItems().stream().map(BillItemE::new).collect(toList());
  }
}
