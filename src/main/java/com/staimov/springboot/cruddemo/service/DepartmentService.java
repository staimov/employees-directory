package com.staimov.springboot.cruddemo.service;

import com.staimov.springboot.cruddemo.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    List<Department> findAllByOrderByLastNameAsc();
    Department findById(int id);
    Department save(Department department);
    void deleteById(int id);
}
