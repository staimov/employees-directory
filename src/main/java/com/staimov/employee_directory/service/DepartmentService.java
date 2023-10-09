package com.staimov.employee_directory.service;

import com.staimov.employee_directory.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    List<Department> findAllByOrderByNameAsc();
    Department findById(int id);
    Department save(Department department);
    void deleteById(int id);
}
