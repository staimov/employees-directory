package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Hello;
import com.staimov.employee_directory.service.HelloService;
import com.staimov.employee_directory.service.HelloServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/hello", produces = "application/hal+json")
public class HelloController {
    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("")
    public EntityModel<Hello> helloWorld(HttpServletRequest request) {
        Hello hello = helloService.sayHello("World");
        return EntityModel.of(hello,
                linkTo(methodOn(HelloController.class).helloWorld(null))
                        .withSelfRel());
    }

    @GetMapping("/{name}")
    public EntityModel<Hello> helloByName(HttpServletRequest request,
                                                 @PathVariable String name) {
        Hello hello = helloService.sayHello(name);
        return EntityModel.of(hello,
                linkTo(methodOn(HelloController.class).helloByName(null, name))
                        .withSelfRel());
    }
}
