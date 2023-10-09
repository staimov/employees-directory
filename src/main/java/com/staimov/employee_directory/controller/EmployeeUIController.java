package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Employee;
import com.staimov.employee_directory.service.DepartmentService;
import com.staimov.employee_directory.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/view/employees")
@SessionAttributes({"page", "limit"})
public class EmployeeUIController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeUIController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // add mapping for "/showList"
    @GetMapping("/showList")
    public String listEmployees(HttpServletRequest request, Model model,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
                                @RequestParam(value = "sortField", required = false, defaultValue = "lastName") String sortField,
                                @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir) {

        Page<Employee> employeePage = employeeService.findAllPaginated(page - 1, limit, sortField, sortDir);
        int totalPages = employeePage.getTotalPages();
        // add to the spring model
        model.addAttribute("employees", employeePage.toList());
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "employees/employee-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(HttpServletRequest request, Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(HttpServletRequest request, @RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(HttpServletRequest request, @ModelAttribute("employee") Employee employee,
                               RedirectAttributes redirectAttributes, Model model) {

        employeeService.save(employee);
        return redirectToListPage(redirectAttributes, model);
    }

    @GetMapping("/delete")
    public String deleteEmployee(HttpServletRequest request, @RequestParam("employeeId") int id,
                                 RedirectAttributes redirectAttributes, Model model) {

        employeeService.deleteById(id);
        return redirectToListPage(redirectAttributes, model);
    }

    private String redirectToListPage(RedirectAttributes redirectAttributes, Model model) {
        Object obj;
        obj = model.getAttribute("page");
        if (obj != null) redirectAttributes.addAttribute("page", obj);
        obj = model.getAttribute("limit");
        if (obj != null) redirectAttributes.addAttribute("limit", obj);

        return "redirect:/view/employees/showList";
    }
}
