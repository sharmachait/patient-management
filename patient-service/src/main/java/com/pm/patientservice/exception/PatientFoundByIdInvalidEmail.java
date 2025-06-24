package com.pm.patientservice.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PatientFoundByIdInvalidEmail extends RuntimeException {
    public PatientFoundByIdInvalidEmail(String s) {
        super(s);
    }
}
