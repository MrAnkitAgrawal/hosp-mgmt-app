package com.nkit.hospmgmtapp.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "PATIENT")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientEntity implements Serializable {

    private Long patientId;
    private String givenName;
    private String middleName;
    private String Surname;
    private LocalDate dob;
    private Gender gender;
    private String whatsAppNumber;
    private String otherMobileNumber;
    private String emailId;
}
