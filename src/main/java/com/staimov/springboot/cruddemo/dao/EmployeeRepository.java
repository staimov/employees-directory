package com.staimov.springboot.cruddemo.dao;

import com.staimov.springboot.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends
        JpaRepository<Employee, Integer>,
        PagingAndSortingRepository<Employee, Integer> {
    // that's it... no need to write any code LOL!

    // add a method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();
}
