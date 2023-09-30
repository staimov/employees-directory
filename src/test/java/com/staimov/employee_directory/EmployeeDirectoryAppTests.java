package com.staimov.employee_directory;

import com.staimov.employee_directory.controller.HelloController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeDirectoryAppTests {

	@Autowired
	private HelloController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
