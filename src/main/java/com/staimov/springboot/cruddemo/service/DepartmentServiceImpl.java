package com.staimov.springboot.cruddemo.service;

import com.staimov.springboot.cruddemo.dao.DepartmentRepository;
import com.staimov.springboot.cruddemo.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> findAllByOrderByLastNameAsc() {
        return departmentRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Department findById(int id) {
        Optional<Department> result = departmentRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteById(int id) {
        departmentRepository.deleteById(id);
    }
}
