package com.staimov.springboot.cruddemo.service;

import com.staimov.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    List<Employee> findAllByOrderByLastNameAsc();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
