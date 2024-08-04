package com.nkit.hospmgmtapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManagementApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(HospitalManagementApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {}
}
