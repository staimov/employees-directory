package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Department;
import com.staimov.employee_directory.service.DepartmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/departments")
public class DepartmentUIController {
    private DepartmentService departmentService;

    public DepartmentUIController(HttpServletRequest request, DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/showList")
    public String listDepartments(HttpServletRequest request, Model model) {
        // add to the spring model
        model.addAttribute("departments", departmentService.findAllByOrderByNameAsc());
        return "departments/department-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(HttpServletRequest request, Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        return "departments/department-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(HttpServletRequest request, @RequestParam("departmentId") int id, Model model) {
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "departments/department-form";
    }

    @PostMapping("/save")
    public String saveDepartment(HttpServletRequest request, @ModelAttribute("department") Department department) {
        departmentService.save(department);
        return "redirect:/view/departments/showList";
    }

    @GetMapping("/delete")
    public String deleteDepartment(HttpServletRequest request, @RequestParam("departmentId") int id) {
        departmentService.deleteById(id);
        return "redirect:/view/departments/showList";
    }
}
