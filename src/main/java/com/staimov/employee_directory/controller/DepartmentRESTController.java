package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.entity.Employee;
import com.staimov.employee_directory.service.DepartmentService;
import com.staimov.employee_directory.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/departments")
public class DepartmentRESTController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final DepartmentModelAssembler departmentModelAssembler;
    private final EmployeeModelAssembler employeeModelAssembler;

    public DepartmentRESTController(EmployeeService employeeService,
                                    DepartmentService departmentService,
                                    EmployeeModelAssembler employeeModelAssembler,
                                    DepartmentModelAssembler departmentModelAssembler) {

        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.departmentModelAssembler = departmentModelAssembler;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<Department>> getAllDepartments(HttpServletRequest request) {

        List<EntityModel<Department>> departmentModels = departmentService.findAll().stream()
                .map(departmentModelAssembler::toModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(DepartmentRESTController.class).getAllDepartments(null))
                .withSelfRel();

        return CollectionModel.of(departmentModels, selfLink);
    }

    @GetMapping("/{departmentId}")
    public EntityModel<Department> getDepartmentById(HttpServletRequest request,
                                                     @PathVariable int departmentId) {

        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new ResourceNotFoundException("Could not find department " + departmentId);
        }

        return departmentModelAssembler.toModel(department);
    }

    @GetMapping("/{departmentId}/employees")
    public CollectionModel<EntityModel<Employee>> getEmployeesByDepartmentId(HttpServletRequest request,
                                                             @PathVariable int departmentId) {

        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new ResourceNotFoundException("Could not find department " + departmentId);
        }

        Set<Employee> employees = department.getEmployees();
        List<EntityModel<Employee>> employeeModels = employees.stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(DepartmentRESTController.class).getEmployeesByDepartmentId(null, departmentId))
                .withSelfRel();

        return CollectionModel.of(employeeModels, selfLink);
    }

    @PostMapping("")
    public ResponseEntity<?> addDepartment(HttpServletRequest request, @RequestBody Department newDepartment) {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        newDepartment.setId(0);

        EntityModel<Department> departmentModel = departmentModelAssembler
                .toModel(departmentService.save(newDepartment));

        return ResponseEntity
                .created(departmentModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(departmentModel);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<?> updateDepartment(HttpServletRequest request,
                                       @RequestBody Department department,
                                       @PathVariable int departmentId) {

        Department departmentToUpdate = departmentService.findById(departmentId);
        if (departmentToUpdate == null) {
            throw new ResourceNotFoundException("Could not find department " + departmentId);
        }

        departmentToUpdate.setName(department.getName());

        EntityModel<Department> departmentModel = departmentModelAssembler
                .toModel(departmentService.save(departmentToUpdate));

        return ResponseEntity
                .created(departmentModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(departmentModel);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartment(HttpServletRequest request,
                                   @PathVariable int departmentId) {

        Department departmentToDelete = departmentService.findById(departmentId);

        if (departmentToDelete == null) {
            throw new ResourceNotFoundException("Could not find department " + departmentId);
        }

        departmentService.deleteById(departmentId);

        return ResponseEntity.noContent().build();
    }
}
