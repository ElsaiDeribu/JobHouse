package com.jobhouse.finder.Controllers;

import java.util.List;

import com.jobhouse.finder.Helpers.UserHelper;
import com.jobhouse.finder.Repositories.CompanyProfileRepository;
import com.jobhouse.finder.Repositories.EmployeeProfileRepository;
import com.jobhouse.finder.Repositories.PostRepository;
import com.jobhouse.finder.Repositories.RoleRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.CompanyProfile;
import com.jobhouse.finder.Tables.EmployeeProfile;
import com.jobhouse.finder.Tables.Post;
import com.jobhouse.finder.Tables.Role;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
@RequestMapping("/adminProfile")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private CompanyProfileRepository companyProfileRepository;
    @Autowired
    private PostRepository postRepo;

    @GetMapping
    public String adminPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("userDetails", userDetails);
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "adminPage";
    }

    @GetMapping("/{username}/makeAdmin")
    public String makeAdmin(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findById(3);
        user.setRole(null);
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/adminProfile";
    }

    @GetMapping("/{username}/DeleteAccount")
    public String deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        Iterable<Post> posts = postRepo.findByUserId(user.getId());
        for (Post post : posts) {
            post.setUser(null);
            postRepo.save(post);
            postRepo.delete(post);
        }
        if (user.getRole().getName().equals("Employee")) {
            EmployeeProfile emp = user.getEmployeeProfile();
            if(emp!=null){
            emp.setUser(null);
            employeeProfileRepository.delete(emp);
            }
        } else {
            CompanyProfile cmp = user.getCompanyProfile();
            if (cmp!=null){
                cmp.setUser(null);
                companyProfileRepository.delete(cmp);
            }
            
        }
        user.setRole(null);
        userRepository.delete(user);
        return "redirect:/adminProfile";
    }

    @GetMapping("/Search")
    public String search(@Param("keyword") String keyword, Model model) {
        User list = userRepository.findByUsername(keyword);
        model.addAttribute("users", list);
        return "search";
    }
}
