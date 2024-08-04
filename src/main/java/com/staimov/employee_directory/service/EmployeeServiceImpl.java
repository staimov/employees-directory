package com.staimov.employee_directory.service;

import com.staimov.employee_directory.dao.EmployeeRepository;
import com.staimov.employee_directory.entity.Employee;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> result = employeeRepository.findById(id);
        return result.orElse(null);
    }

    // no need @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    // no need @Transactional
    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public long count() {
        return employeeRepository.count();
    }

    @Override
    @Timed(value = "getEmployees", description = "Time taken to get employees")
    public Page<Employee> findAllPaginated(int offset, int count, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(offset, count, sort);

        return employeeRepository.findAll(pageable);
    }
}
