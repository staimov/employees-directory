package com.staimov.employee_directory;

import com.staimov.employee_directory.controller.EmployeeRESTController;
import com.staimov.employee_directory.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class EmployeeDirectoryAppTests {

	@Autowired
	private HelloController helloController;
	@Autowired
	private EmployeeRESTController employeeRESTController;

	@Test
	void contextLoads() {
		assertAll(
				() -> assertThat(helloController).isNotNull(),
				() -> assertThat(employeeRESTController).isNotNull()
		);
	}
}
