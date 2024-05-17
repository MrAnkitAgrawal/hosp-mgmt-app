package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
}
