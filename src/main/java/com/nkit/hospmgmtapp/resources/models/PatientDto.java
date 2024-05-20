package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;
import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseDateToString;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nkit.hospmgmtapp.domain.entities.PatientE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonPropertyOrder(value = {"id"})
public class PatientDto {
  @JsonProperty("patientId")
  private Long id;

  @JsonProperty("firstName")
  @NotBlank(message = PATIENT_FIRST_NAME_NOT_PROVIDED)
  private String firstName;

  @JsonProperty("middleName")
  private String middleName;

  @JsonProperty("lastName")
  @NotBlank(message = PATIENT_LAST_NAME_NOT_PROVIDED)
  private String lastName;

  @JsonProperty("dateOfBirth")
  @NotBlank(message = PATIENT_DOB_NOT_PROVIDED)
  private String dob;

  @JsonProperty("gender")
  @NotBlank(message = "PATIENT_GENDER_NOT_PROVIDED")
  private String gender;

  @JsonProperty("mobileNumber")
  @NotBlank(message = PATIENT_MOBILE_NUMBER_NOT_PROVIDED)
  private String otherMobileNumber;

  @JsonProperty("whatsAppNumber")
  private String whatsAppNumber;

  @JsonProperty("emailId")
  @Email(message = PATIENT_EMAIL_ID_INVALID)
  private String emailId;

  public PatientDto(PatientE patientE) {
    this.id = patientE.getPatientId();
    this.firstName = patientE.getFirstName();
    this.middleName = patientE.getMiddleName();
    this.lastName = patientE.getLastName();
    this.dob = parseDateToString(patientE.getDob());
    this.gender = patientE.getGender() == null ? null : patientE.getGender().name();
    this.whatsAppNumber = patientE.getWhatsAppNumber();
    this.otherMobileNumber = patientE.getOtherMobileNumber();
    this.emailId = patientE.getEmailId();
  }
}
