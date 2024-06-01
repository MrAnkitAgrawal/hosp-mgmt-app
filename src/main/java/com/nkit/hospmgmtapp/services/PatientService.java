package com.nkit.hospmgmtapp.services;

import static com.nkit.hospmgmtapp.exceptionhandler.ExceptionKey.PATIENT_NOT_FOUND;
import static java.util.stream.Collectors.toList;

import com.nkit.hospmgmtapp.domain.entities.PatientE;
import com.nkit.hospmgmtapp.domain.repos.PatientR;
import com.nkit.hospmgmtapp.resources.models.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientR patientR;

  public Long createPatient(PatientDto patientDto) {
    return patientR.save(new PatientE(patientDto)).getPatientId();
  }

  public PatientDto retrievePatientById(Long patientId) {
    return new PatientDto(getPatientE(patientId));
  }

  public List<PatientDto> retrieveAllPatient() {
    return patientR.findAll().stream().map(PatientDto::new).collect(toList());
  }

  public void updatePatient(Long patientId, PatientDto patientDto) {
    PatientE existingPatientE = getPatientE(patientId);
    PatientE updatedPatientE = new PatientE(patientDto);
    updatedPatientE.setPatientId(existingPatientE.getPatientId());
    patientR.save(updatedPatientE);
  }

  public void removePatient(Long patientId) {
    patientR.delete(getPatientE(patientId));
  }

  private PatientE getPatientE(Long patientId) {
    return patientR.findById(patientId).orElseThrow(() -> new RuntimeException(PATIENT_NOT_FOUND));
  }

  public PatientDto retrievePatientDetailsById(Long patientId) {
    PatientE patientE = getPatientE(patientId);
    PatientDetailsDto patientDetailsDto = new PatientDetailsDto(patientE);

    patientDetailsDto.setBillingDetails(
        patientE.getBills().stream().map(BillingDetailsDto::new).collect(toList()));
    return patientDetailsDto;
  }
}
