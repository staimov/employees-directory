package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.service.DepartmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/departments")
public class DepartmentRESTController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentRESTController.class);

    private DepartmentService departmentService;

    public DepartmentRESTController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public List<Department> findAll(HttpServletRequest request) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(HttpServletRequest request, @PathVariable int id) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());
        Department department = departmentService.findById(id);
        if (department == null) {
            throw new RuntimeException("Department id not found: " + id);
        }

        return department;
    }

    @PostMapping("")
    public Department addDepartment(HttpServletRequest request, @RequestBody Department department) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        department.setId(0);

        Department dbDepartment = departmentService.save(department);

        return dbDepartment;
    }

    @PutMapping("")
    public Department updateDepartment(HttpServletRequest request, @RequestBody Department department) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        Department updatedDepartment = departmentService.save(department);

        return updatedDepartment;
    }

    @DeleteMapping("/{id}")
    public String delete(HttpServletRequest request, @PathVariable int id) {
        logger.debug(request.getMethod() + " " + request.getRequestURL().toString());

        Department foundDepartment = departmentService.findById(id);

        if (foundDepartment == null) {
            throw new RuntimeException("Department id not found: " + id);
        }

        departmentService.deleteById(id);

        return "Deleted department with id: " + id;
    }
}
