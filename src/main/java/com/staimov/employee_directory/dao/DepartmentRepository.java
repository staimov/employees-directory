package com.staimov.employee_directory.dao;

import com.staimov.employee_directory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public List<Department> findAllByOrderByNameAsc();
}
