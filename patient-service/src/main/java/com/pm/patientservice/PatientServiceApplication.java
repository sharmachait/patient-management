package com.pm.patientservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
            title="Patient API",
            description = "CRUD Patient",
            version = "v1",
            contact = @Contact(
                name = "Chaitanya Sharma",
                email = "chait8126@gmail.com",
                url = "https://github.com/sharmachait"
            ),
            license = @License(
                name= "Apache 2.0",
                url = "https://github.com/sharmachait"
            )
        ),
    externalDocs = @ExternalDocumentation(
        description = "Documentation website",
        url = "https://github.com/sharmachait"
    )
)
public class PatientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
    }

}
