package org.mufasadev.mshando.core;

import org.mufasadev.mshando.core.user.models.AppRole;
import org.mufasadev.mshando.core.user.models.Role;
import org.mufasadev.mshando.core.user.repository.RoleRepository;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableAsync
public class MshandoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MshandoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Retrieve or create roles
            Role clientRole = roleRepository.findByName(AppRole.ROLE_CLIENT)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_CLIENT);
                        return roleRepository.save(newUserRole);
                    });

            Role taskerRole = roleRepository.findByName(AppRole.ROLE_TASKER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_TASKER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> clientRoles = Set.of(clientRole);
            Set<Role> taskerRoles = Set.of(taskerRole);
            Set<Role> adminRoles = Set.of(clientRole, taskerRole, adminRole);


            /*/ Create admin user if not already present
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUsername("client").ifPresent(user -> {
                user.setRoles(clientRoles);
                userRepository.save(user);
            });

            userRepository.findByUsername("tasker").ifPresent(seller -> {
                seller.setRoles(taskerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUsername("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });
            */
        };
    }

}