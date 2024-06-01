package com.nkit.hospmgmtapp.resources.handlers;

import static org.springframework.http.HttpStatus.*;

import com.nkit.hospmgmtapp.resources.models.PatientDto;
import com.nkit.hospmgmtapp.services.PatientService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/patient")
@RequiredArgsConstructor
public class PatientController {
  private final PatientService patientService;

  @PostMapping
  public ResponseEntity<Map> addPatient(@RequestBody @Valid PatientDto patientDto) {
    Map<String, Long> patientId = new HashMap<>();
    patientId.put("patientId", patientService.createPatient(patientDto));
    return new ResponseEntity<>(patientId, CREATED);
  }

  @GetMapping("/{patientId}")
  public ResponseEntity<PatientDto> retrievePatient(@PathVariable Long patientId) {
    return new ResponseEntity<>(patientService.retrievePatientById(patientId), OK);
  }

  @GetMapping
  public ResponseEntity<List<PatientDto>> retrievePatient() {
    return new ResponseEntity<>(patientService.retrieveAllPatient(), OK);
  }

  @PutMapping("/{patientId}")
  public ResponseEntity<String> updatePatient(
      @PathVariable Long patientId, @RequestBody @Valid PatientDto patientDto) {
    patientService.updatePatient(patientId, patientDto);
    return new ResponseEntity<>(NO_CONTENT);
  }

  @DeleteMapping("/{patientId}")
  public ResponseEntity<PatientDto> removePatient(@PathVariable Long patientId) {
    patientService.removePatient(patientId);
    return new ResponseEntity<>(NO_CONTENT);
  }

  @GetMapping("/{patientId}/billing")
  public ResponseEntity<PatientDto> retrievePatientBillingDetails(@PathVariable Long patientId) {
    return new ResponseEntity<>(patientService.retrievePatientDetailsById(patientId), OK);
  }
}
