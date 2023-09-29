package com.staimov.employee_directory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employees Directory API", version = "1.0", description = "Employees Information"))
public class EmployeeDirectoryApp {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDirectoryApp.class, args);
	}

}
