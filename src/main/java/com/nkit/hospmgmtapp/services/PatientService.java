package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.PATIENT_NOT_FOUND;
import static java.util.stream.Collectors.toList;

import com.nkit.hospmgmtapp.domain.entities.PatientE;
import com.nkit.hospmgmtapp.domain.repos.InsuranceR;
import com.nkit.hospmgmtapp.domain.repos.PatientR;
import com.nkit.hospmgmtapp.resources.models.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientR patientR;
  private final InsuranceR insuranceR;

  public Long createPatient(PatientDto patientDto) {
    PatientE patientE = new PatientE(patientDto);
    patientE.getInsurances().forEach(i -> i.setPatientE(patientE));

    Long patientId = patientR.save(patientE).getPatientId();
    insuranceR.saveAll(patientE.getInsurances());

    return patientId;
  }

  public PatientDto retrievePatientById(Long patientId) {
    return new PatientDto(getPatientE(patientId));
  }

  public List<PatientDto> retrieveAllPatient() {
    return patientR.findAll().stream().map(PatientDto::new).collect(toList());
  }

  public void updatePatient(Long patientId, PatientDto patientDto) {
    PatientE existingPatientE = getPatientE(patientId);
    existingPatientE.getInsurances().forEach(insuranceR::delete);

    PatientE updatedPatientE = new PatientE(patientDto);
    updatedPatientE.setPatientId(existingPatientE.getPatientId());
    updatedPatientE.getInsurances().forEach(i -> i.setPatientE(updatedPatientE));

    patientR.save(updatedPatientE);
    insuranceR.saveAll(updatedPatientE.getInsurances());
  }

  public void removePatient(Long patientId) {
    PatientE patientE = getPatientE(patientId);
    patientE.getInsurances().forEach(insuranceR::delete);
    patientR.delete(patientE);
  }

  public PatientDto retrievePatientDetailsById(Long patientId) {
    PatientE patientE = getPatientE(patientId);
    PatientDetailsDto patientDetailsDto = new PatientDetailsDto(patientE);

    patientDetailsDto.setBillingDetails(
        patientE.getBills().stream().map(BillingDetailsDto::new).collect(toList()));
    return patientDetailsDto;
  }

  private PatientE getPatientE(Long patientId) {
    return patientR.findById(patientId).orElseThrow(() -> new RuntimeException(PATIENT_NOT_FOUND));
  }
}
