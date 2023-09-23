package com.staimov.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    // add support for JDBC
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**").authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/departments").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/api/departments/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/departments").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/departments").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
                .and().httpBasic()
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain viewFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/view/employees/showList").hasRole("USER")
                        .requestMatchers("/view/employees/showFormForAdd").hasRole("MANAGER")
                        .requestMatchers("/view/employees/showFormForUpdate").hasRole("MANAGER")
                        .requestMatchers("/view/employees/save").hasRole("MANAGER")
                        .requestMatchers("/view/employees/delete").hasRole("ADMIN")
                        .requestMatchers("/view/department/showList").hasRole("USER")
                        .requestMatchers("/view/department/showFormForAdd").hasRole("MANAGER")
                        .requestMatchers("/view/department/showFormForUpdate").hasRole("MANAGER")
                        .requestMatchers("/view/department/save").hasRole("MANAGER")
                        .requestMatchers("/view/department/delete").hasRole("ADMIN")
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                    form
                            .loginPage("/showMyLoginPage")
                            .loginProcessingUrl("/authenticate")
                            .permitAll()
                )
                .logout(logout ->
                        logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}
