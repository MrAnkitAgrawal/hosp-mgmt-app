package com.nkit.hospmgmtapp.domain.entities;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

import com.nkit.hospmgmtapp.resources.models.PatientDto;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PATIENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientE implements Serializable {
  @Serial private static final long serialVersionUID = 2270050187803228595L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_id_seq_gen")
  @SequenceGenerator(
      name = "patient_id_seq_gen",
      sequenceName = "patient_id_seq_gen",
      initialValue = 1)
  @Column(name = "patient_id")
  private Long patientId;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "dob")
  private LocalDate dob;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "aadhar_number")
  private String aadharNumber;

  @Column(name = "whatsapp_number")
  private String whatsAppNumber;

  @Column(name = "mobile_number")
  private String otherMobileNumber;

  @Column(name = "email_id")
  private String emailId;

  @OneToMany(mappedBy = "patientE", fetch = LAZY)
  private List<InsuranceE> insurances = new ArrayList<>();

  @OneToMany(mappedBy = "patientE", fetch = LAZY)
  private List<BillingE> bills = new ArrayList<>();

  @OneToMany(mappedBy = "patientE", fetch = LAZY)
  private List<DialysisScheduleE> dialysisSchedules = new ArrayList<>();

  public PatientE(PatientDto patientDto) {
    this.firstName = patientDto.getFirstName();
    this.middleName = patientDto.getMiddleName();
    this.lastName = patientDto.getLastName();
    this.dob = parseStringToDate(patientDto.getDob());
    this.gender = getEnumIgnoreCase(Gender.class, patientDto.getGender());
    this.aadharNumber = patientDto.getAadharNumber();
    this.whatsAppNumber = patientDto.getWhatsAppNumber();
    this.otherMobileNumber = patientDto.getOtherMobileNumber();
    this.emailId = patientDto.getEmailId();
    this.insurances = patientDto.getInsurances().stream().map(InsuranceE::new).collect(toList());
  }
}
