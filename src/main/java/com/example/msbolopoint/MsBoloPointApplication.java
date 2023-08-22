package com.example.msbolopoint;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.example.msbolopoint.config.CorsFilter;
import com.example.msbolopoint.model.Role;
import com.example.msbolopoint.repo.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MsBoloPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBoloPointApplication.class, args);
    }

    @Bean
    public JtsModule jtsModule() {
        // This module will provide a Serializer for geometries
        return new JtsModule();
    }
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }
    @Bean
    public CommandLineRunner demo(RoleRepository roleRepo) {
        return (args) -> {
            Role role=new Role();
            role.setName("ROLE_ADMIN");
            roleRepo.save(role);

            role=new Role();
            role.setName("ROLE_USER");
            roleRepo.save(role);
        };
    }
}
