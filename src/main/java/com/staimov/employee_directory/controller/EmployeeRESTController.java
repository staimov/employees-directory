package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.entity.Employee;
import com.staimov.employee_directory.service.DepartmentService;
import com.staimov.employee_directory.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/employees", produces = "application/hal+json")
public class EmployeeRESTController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final EmployeeModelAssembler employeeModelAssembler;

    public EmployeeRESTController(EmployeeService employeeService,
                                  DepartmentService departmentService,
                                  EmployeeModelAssembler employeeModelAssembler) {

        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<Employee>> getAllEmployees(HttpServletRequest request) {

        List<EntityModel<Employee>> employeeModels = employeeService.findAll().stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(EmployeeRESTController.class).getAllEmployees(null))
                .withSelfRel();

        return CollectionModel.of(employeeModels, selfLink);
    }

    @GetMapping("/{employeeId}")
    public @ResponseBody EntityModel<Employee> getEmployeeById(HttpServletRequest request,
                    @PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new ResourceNotFoundException("Could not find employee " + employeeId);
        }

        return employeeModelAssembler.toModel(employee);
    }

    @GetMapping("/{employeeId}/department")
    public EntityModel<Department> getDepartmentByEmployeeId(HttpServletRequest request,
                                                             @PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new ResourceNotFoundException("Could not find employee " + employeeId);
        }

        Department department = employee.getDepartment();
        EntityModel<Department> departmentModel = EntityModel.of(department);

        departmentModel.add(linkTo(methodOn(DepartmentRESTController.class).getAllDepartments(null))
                .withRel("departments"));

        departmentModel.add(linkTo(methodOn(EmployeeRESTController.class).getDepartmentByEmployeeId(null, employeeId))
                .withSelfRel());

        return departmentModel;
    }

    @PostMapping("")
    public ResponseEntity<?> addEmployee(HttpServletRequest request, @RequestBody Employee newEmployee) {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        newEmployee.setId(0);

        int departmentId = newEmployee.getDepartment().getId();
        Department foundDepartment = departmentService.findById(departmentId);
        if (foundDepartment == null) {
            throw new ResourceNotFoundException("Could not find department " + departmentId);
        }

        newEmployee.setDepartment(foundDepartment);

        EntityModel<Employee> employeeModel = employeeModelAssembler
                .toModel(employeeService.save(newEmployee));

        return ResponseEntity
                .created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeModel);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(HttpServletRequest request,
                                            @RequestBody Employee employee,
                                            @PathVariable int employeeId) {

        Employee employeeToUpdate = employeeService.findById(employeeId);
        if (employeeToUpdate == null) {
            throw new ResourceNotFoundException("Could not find employee " + employeeId);
        }

        int departmentId = employee.getDepartment().getId();
        Department foundDepartment = departmentService.findById(departmentId);
        if (foundDepartment == null) {
            throw new ResourceNotFoundException("Could not find department " + departmentId);
        }

        employeeToUpdate.setDepartment(foundDepartment);
        employeeToUpdate.setEmail(employee.getEmail());
        employeeToUpdate.setFirstName(employee.getFirstName());
        employeeToUpdate.setLastName(employee.getLastName());

        EntityModel<Employee> employeeModel = employeeModelAssembler
                .toModel(employeeService.save(employeeToUpdate));

        return ResponseEntity
                .created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeModel);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployeeById(HttpServletRequest request,
                                                @PathVariable int employeeId) {

        Employee employeeToDelete = employeeService.findById(employeeId);

        if (employeeToDelete == null) {
            throw new ResourceNotFoundException("Could not find employee " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return ResponseEntity.noContent().build();
    }
}
