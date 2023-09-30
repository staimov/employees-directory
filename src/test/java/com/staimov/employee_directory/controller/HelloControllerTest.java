package com.staimov.employee_directory.controller;

import com.staimov.employee_directory.entity.Hello;
import com.staimov.employee_directory.service.HelloService;
import com.staimov.employee_directory.service.HelloServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {
    private static String BASE_PATH = "http://localhost/api/hello";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloService helloService;

    @Test
    public void helloWorldShouldReturnDefaultMessage() throws Exception {
        String name = "World";
        String helloMessage = "Hello, " + name + "!";
        String path = BASE_PATH;

        when(helloService.sayHello(name)).thenReturn(new Hello(helloMessage));

        mockMvc.perform(get(path)
                        .with(user("user").password("user").roles("USER")))
                .andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.helloMessage",
                        is(helloMessage)))
                .andExpect(jsonPath("$._links.self.href",
                        is(path)));
    }

    @Test
    public void helloByNameShouldReturnNamedMessage() throws Exception {
        String name = "Foo";
        String helloMessage = "Hello, " + name + "!";
        String path = BASE_PATH + "/" + name;

        when(helloService.sayHello(name)).thenReturn(new Hello(helloMessage));

        mockMvc.perform(get(path)
                        .with(user("user").password("user").roles("USER")))
                .andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.helloMessage",
                        is(helloMessage)))
                .andExpect(jsonPath("$._links.self.href",
                        is(path)));;
    }
}
