package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.controller.assembler.EmployeeModelAssembler;
import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.entity.Employee;
import com.staimov.employee_directory.service.DepartmentService;
import com.staimov.employee_directory.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(EmployeeRESTController.class)
@Import(EmployeeModelAssembler.class)
public class EmployeeRESTControllerTest {
    private static String BASE_PATH = "http://localhost/api/employees";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;

    @Test
    public void getEmployeeByIdWithValidIdShouldReturnEmployee() throws Exception {
        int employeeId = 1;
        String path = BASE_PATH + "/" + employeeId;
        Department department = new Department();
        department.setName("Baz");
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Foo")
                .lastName("Bar")
                .email("foo@mail.com")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .department(department)
                .build();


        when(employeeService.findById(employeeId)).thenReturn(employee);

        mockMvc.perform(get(path)
                        .with(user("user").password("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.id",
                        is(employeeId)))
                .andExpect(jsonPath("$.updateTime",
                        is(employee.getUpdateTime().format(DateTimeFormatter.ISO_DATE_TIME))))
                .andExpect(jsonPath("$.createTime",
                        is(employee.getCreateTime().format(DateTimeFormatter.ISO_DATE_TIME))))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())))
                .andExpect(jsonPath("$._links.self.href",
                        is(path)))
                .andExpect(jsonPath("$._links.employees.href",
                        is(BASE_PATH)))
                .andExpect(jsonPath("$._links.department.href",
                        is(path + "/department")));
    }

    @Test
    public void getEmployeeByIdWithInvalidIdShouldReturn404Error() throws Exception {
        String path = BASE_PATH + "/999";

        when(employeeService.findById(anyInt())).thenReturn(null);

        mockMvc.perform(get(path)
                        .with(user("user").password("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEmployeesUnauthorizedShouldReturn401Error() throws Exception {
        mockMvc.perform(get(BASE_PATH))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
