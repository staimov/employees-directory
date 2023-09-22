package com.staimov.springboot.cruddemo.controller;

import com.staimov.springboot.cruddemo.entity.Employee;
import com.staimov.springboot.cruddemo.service.DepartmentService;
import com.staimov.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // add mapping for "/showList"
    @GetMapping("/showList")
    public String listEmployees(Model model) {
        // add to the spring model
        model.addAttribute("employees", employeeService.findAllByOrderByLastNameAsc());
        return "/employees/employee-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "/employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "/employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employee.setDepartment(departmentService.findById(1));
        employeeService.save(employee);
        return "redirect:/employees/showList";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id) {
        employeeService.deleteById(id);
        return "redirect:/employees/showList";
    }
}
