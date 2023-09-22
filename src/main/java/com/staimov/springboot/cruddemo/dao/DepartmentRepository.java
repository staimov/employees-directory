package com.staimov.springboot.cruddemo.dao;

import com.staimov.springboot.cruddemo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public List<Department> findAllByOrderByNameAsc();
}
