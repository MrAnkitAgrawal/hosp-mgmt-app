package com.nkit.hospmgmtapp.resources.handlers;

import static org.springframework.http.HttpStatus.CREATED;

import com.nkit.hospmgmtapp.resources.models.PatientDto;
import com.nkit.hospmgmtapp.services.models.Appointment;
import com.nkit.hospmgmtapp.services.serviceextns.DialysisScheduleServiceExtn;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dialysisScheduler")
@RequiredArgsConstructor
public class DialysisSchedulerController {
    private final DialysisScheduleServiceExtn extn;

    @PostMapping
    public ResponseEntity<Set> addPatient(@RequestBody PatientDto patientDto) {
        Set<Appointment> availableAppointments = extn.dialysisSchedules();
        return new ResponseEntity<>(availableAppointments, CREATED);
    }

}
