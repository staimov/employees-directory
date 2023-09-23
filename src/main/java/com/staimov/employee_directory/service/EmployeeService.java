package com.staimov.employee_directory.service;

import com.staimov.employee_directory.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    List<Employee> findAllByOrderByLastNameAsc();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
