package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/departments")
public class DepartmentRESTController {
    private DepartmentService departmentService;

    public DepartmentRESTController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public List<Department> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable int id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            throw new RuntimeException("Department id not found: " + id);
        }

        return department;
    }

    @PostMapping("")
    public Department addDepartment(@RequestBody Department department) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        department.setId(0);

        Department dbDepartment = departmentService.save(department);

        return dbDepartment;
    }

    @PutMapping("")
    public Department updateDepartment(@RequestBody Department department) {

        Department updatedDepartment = departmentService.save(department);

        return updatedDepartment;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        Department foundDepartment = departmentService.findById(id);

        if (foundDepartment == null) {
            throw new RuntimeException("Department id not found: " + id);
        }

        departmentService.deleteById(id);

        return "Deleted department with id: " + id;
    }
}
