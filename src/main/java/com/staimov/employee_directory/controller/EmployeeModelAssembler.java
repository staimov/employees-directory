package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        int employeeId = employee.getId();
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeRESTController.class).getEmployeeById(null, employeeId))
                        .withSelfRel(),
                linkTo(methodOn(EmployeeRESTController.class).getAllEmployees(null))
                        .withRel("employees"),
                linkTo(methodOn(EmployeeRESTController.class).getDepartmentByEmployeeId(null, employeeId))
                        .withRel("department").withName(employee.getDepartment().getName()));
    }
}
