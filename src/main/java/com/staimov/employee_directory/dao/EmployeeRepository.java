package com.staimov.employee_directory.dao;

import com.staimov.employee_directory.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends
        JpaRepository<Employee, Integer>,
        PagingAndSortingRepository<Employee, Integer> {
    // that's it... no need to write any code LOL!
}
