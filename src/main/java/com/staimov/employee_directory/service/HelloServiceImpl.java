package com.staimov.employee_directory.service;

import com.staimov.employee_directory.entity.Hello;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    public Hello sayHello(String name) {
        return new Hello("Hello, " + name + "!");
    }
}
