package com.nkit.hospmgmtapp;

import com.nkit.hospmgmtapp.domain.entities.PatientE;
import com.nkit.hospmgmtapp.domain.repos.PatientR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManagementApplication implements CommandLineRunner {
  @Autowired private PatientR patientR;

  public static void main(String[] args) {
    SpringApplication.run(HospitalManagementApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    PatientE patientE = new PatientE();
    patientE.setFirstName("First");
    patientE.setLastName("Last");
    patientR.save(patientE);
  }
}
