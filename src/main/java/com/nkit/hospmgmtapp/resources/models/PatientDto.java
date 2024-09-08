package com.nkit.hospmgmtapp.resources.models;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.*;
import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseDateToString;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nkit.hospmgmtapp.domain.entities.Gender;
import com.nkit.hospmgmtapp.domain.entities.PatientE;
import com.nkit.hospmgmtapp.validators.annotations.NotFutureDate;
import com.nkit.hospmgmtapp.validators.annotations.ValidEnum;
import com.nkit.hospmgmtapp.validators.annotations.ValidNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  private String lastName;

  @JsonProperty("dateOfBirth")
  @NotBlank(message = PATIENT_DOB_NOT_PROVIDED)
  @NotFutureDate(message = PATIENT_DOB_IS_INVALID)
  private String dob;

  @JsonProperty("gender")
  @NotNull(message = PATIENT_GENDER_NOT_PROVIDED)
  @ValidEnum(targetClassType = Gender.class, message = PATIENT_GENDER_INVALID)
  private String gender;

  @JsonProperty("aadharNumber")
  @ValidNumber(length = 12, message = AADHAR_NUMBER_INVALID)
  private String aadharNumber;

  @JsonProperty("mobileNumber")
  @NotBlank(message = PATIENT_MOBILE_NUMBER_NOT_PROVIDED)
  private String otherMobileNumber;

  @JsonProperty("whatsAppNumber")
  private String whatsAppNumber;

  @JsonProperty("emailId")
  @Email(message = PATIENT_EMAIL_ID_INVALID)
  private String emailId;

  @NotNull(message = PATIENT_INSURANCE_IS_NULL)
  @JsonProperty("insuranceDetails")
  private List<InsuranceDto> insurances = new ArrayList<>();

  public PatientDto(PatientE patientE) {
    this.id = patientE.getPatientId();
    this.firstName = patientE.getFirstName();
    this.middleName = patientE.getMiddleName();
    this.lastName = patientE.getLastName();
    this.dob = parseDateToString(patientE.getDob());
    this.gender = patientE.getGender() == null ? null : patientE.getGender().name();
    this.aadharNumber = patientE.getAadharNumber();
    this.whatsAppNumber = patientE.getWhatsAppNumber();
    this.otherMobileNumber = patientE.getOtherMobileNumber();
    this.emailId = patientE.getEmailId();
    this.insurances =
        patientE.getInsurances().stream().map(InsuranceDto::new).collect(Collectors.toList());
  }
}
