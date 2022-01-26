package com.jobhouse.finder;

import com.jobhouse.finder.Repositories.RoleRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.Role;
import com.jobhouse.finder.Tables.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class FinderApplication {

   @Autowired
   private UserRepository userRepo;
   @Autowired
   private RoleRepository roleRepo;

   public static void main(String[] args) {
      SpringApplication.run(FinderApplication.class, args);
   }

   @Bean
   public CommandLineRunner populateRoles() {
      return args -> {
         Role r = roleRepo.findById(3);
         if (r==null){
            Role roleEmployee = new Role(1, "Employee");
            Role roleCompany = new Role(2, "Company");
            roleRepo.save(roleEmployee);
            roleRepo.save(roleCompany);
         
            User user = new User();
            Role roleAdmin = new Role(3, "Admin");
            user.setFullName("Admin");
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");

            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode("admin"));
            user.setEnabled(true);
            user.setRole(roleAdmin);
            userRepo.save(user);
         }
      };
   }

}
