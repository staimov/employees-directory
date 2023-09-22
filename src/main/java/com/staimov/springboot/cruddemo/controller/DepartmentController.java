package com.staimov.springboot.cruddemo.controller;

import com.staimov.springboot.cruddemo.entity.Department;
import com.staimov.springboot.cruddemo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/showList")
    public String listDepartments(Model model) {
        // add to the spring model
        model.addAttribute("departments", departmentService.findAllByOrderByLastNameAsc());
        return "/departments/department-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        return "/departments/department-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("departmentId") int id, Model model) {
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "/departments/department-form";
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        return "redirect:/departments/showList";
    }

    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam("departmentId") int id) {
        departmentService.deleteById(id);
        return "redirect:/departments/showList";
    }
}
