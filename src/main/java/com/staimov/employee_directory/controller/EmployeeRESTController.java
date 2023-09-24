package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.entity.Employee;
import com.staimov.employee_directory.service.DepartmentService;
import com.staimov.employee_directory.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeRESTController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRESTController.class);

    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public EmployeeRESTController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public List<Employee> findAll(HttpServletRequest request) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(HttpServletRequest request, @PathVariable int id) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new RuntimeException("Employee id not found: " + id);
        }

        return employee;
    }

    @PostMapping("")
    public Employee addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        employee.setId(0);

        int departmentId = employee.getDepartment().getId();
        Department foundDepartment = departmentService.findById(departmentId);
        if (foundDepartment == null) {
            throw new RuntimeException("Department id not found: " + departmentId);
        }

        employee.setDepartment(foundDepartment);
        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }

    @PutMapping("")
    public Employee updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        int departmentId = employee.getDepartment().getId();
        Department foundDepartment = departmentService.findById(departmentId);
        if (foundDepartment == null) {
            throw new RuntimeException("Department id not found: " + departmentId);
        }

        employee.setDepartment(foundDepartment);

        Employee updatedEmployee = employeeService.save(employee);

        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    public String delete(HttpServletRequest request, @PathVariable int id) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        Employee foundEmployee = employeeService.findById(id);

        if (foundEmployee == null) {
            throw new RuntimeException("Employee id not found: " + id);
        }

        employeeService.deleteById(id);

        return "Deleted employee with id: " + id;
    }
}
