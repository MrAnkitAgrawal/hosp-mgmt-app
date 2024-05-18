package com.nkit.hospmgmtapp.domain.entities;

import static jakarta.persistence.FetchType.LAZY;

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

  @Column(name = "whatsapp_number")
  private String whatsAppNumber;

  @Column(name = "mobile_number")
  private String otherMobileNumber;

  @Column(name = "email_id")
  private String emailId;

  @OneToMany(mappedBy = "patientE", fetch = LAZY)
  private List<BillingE> bills = new ArrayList<>();

  @OneToMany(mappedBy = "patientE", fetch = LAZY)
  private List<DialysisScheduleE> dialysisSchedules = new ArrayList<>();
}
