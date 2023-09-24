package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Employee;
import com.staimov.employee_directory.service.DepartmentService;
import com.staimov.employee_directory.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/view/employees")
public class EmployeeUIController {

    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public EmployeeUIController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // add mapping for "/showList"
    @GetMapping("/showList")
    public String listEmployees(HttpServletRequest request, Model model) {
        // add to the spring model
        model.addAttribute("employees", employeeService.findAllByOrderByLastNameAsc());
        return "/employees/employee-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(HttpServletRequest request, Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        return "/employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(HttpServletRequest request, @RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        return "/employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(HttpServletRequest request, @ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/view/employees/showList";
    }

    @GetMapping("/delete")
    public String deleteEmployee(HttpServletRequest request, @RequestParam("employeeId") int id) {
        employeeService.deleteById(id);
        return "redirect:/view/employees/showList";
    }
}
