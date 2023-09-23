package com.staimov.springboot.cruddemo.controller;

import com.staimov.springboot.cruddemo.entity.Department;
import com.staimov.springboot.cruddemo.entity.Employee;
import com.staimov.springboot.cruddemo.service.DepartmentService;
import com.staimov.springboot.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeRESTController {
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public EmployeeRESTController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new RuntimeException("Employee id not found: " + id);
        }

        return employee;
    }

    @PostMapping("")
    public Employee addEmployee(@RequestBody Employee employee) {

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
    public Employee updateEmployee(@RequestBody Employee employee) {

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
    public String delete(@PathVariable int id) {
        Employee foundEmployee = employeeService.findById(id);

        if (foundEmployee == null) {
            throw new RuntimeException("Employee id not found: " + id);
        }

        employeeService.deleteById(id);

        return "Deleted employee with id: " + id;
    }
}
