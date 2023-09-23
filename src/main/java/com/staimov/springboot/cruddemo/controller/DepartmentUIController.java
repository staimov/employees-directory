package com.staimov.springboot.cruddemo.controller;

import com.staimov.springboot.cruddemo.entity.Department;
import com.staimov.springboot.cruddemo.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/departments")
public class DepartmentUIController {
    private DepartmentService departmentService;

    public DepartmentUIController(DepartmentService departmentService) {
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
        return "redirect:/view/departments/showList";
    }

    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam("departmentId") int id) {
        departmentService.deleteById(id);
        return "redirect:/view/departments/showList";
    }
}
