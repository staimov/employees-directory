package com.staimov.employee_directory.controller.assembler;

import com.staimov.employee_directory.controller.DepartmentRESTController;
import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.entity.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DepartmentModelAssembler implements RepresentationModelAssembler<Department, EntityModel<Department>> {

    @Override
    public EntityModel<Department> toModel(Department department) {
        int departmentId = department.getId();
        return EntityModel.of(department,
                WebMvcLinkBuilder.linkTo(methodOn(DepartmentRESTController.class).getDepartmentById(null, departmentId))
                        .withSelfRel(),
                linkTo(methodOn(DepartmentRESTController.class).getAllDepartments(null))
                        .withRel("departments"),
                linkTo(methodOn(DepartmentRESTController.class).getEmployeesByDepartmentId(null, departmentId))
                        .withRel("employees"));
    }
}
