package com.example.micro_lending.Project.Configuration;

import com.example.micro_lending.Project.Entity.Role;
import com.example.micro_lending.Project.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");
        for (String roleName : roles) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role saved = roleRepository.save(Role.builder().name(roleName).build());
                        System.out.println("Inserted role: " + saved.getName() + " id=" + saved.getId());
                        return saved;
                    });
        }
    }
}
