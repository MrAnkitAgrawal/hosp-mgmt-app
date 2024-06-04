package com.nkit.hospmgmtapp.domain.entities;

import com.nkit.hospmgmtapp.resources.models.InsuranceDto;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "INSURANCE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InsuranceE implements Serializable {
  @Serial private static final long serialVersionUID = -7208073958703806319L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_id_seq_gen")
  @SequenceGenerator(
      name = "insurance_id_seq_gen",
      sequenceName = "insurance_id_seq_gen",
      initialValue = 1)
  @Column(name = "insurance_id")
  private Long insuranceId;

  @Column(name = "insurance_company")
  private String insuranceCompany;

  @Column(name = "policy_number")
  private String policyNumber;

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
  private PatientE patientE;

  public InsuranceE(InsuranceDto insuranceDto) {
    this.insuranceCompany = insuranceDto.getInsuranceCompany();
    this.policyNumber = insuranceDto.getPolicyNumber();
  }
}
