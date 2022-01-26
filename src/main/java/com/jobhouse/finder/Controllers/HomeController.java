package com.jobhouse.finder.Controllers;

import java.util.Collection;

import com.jobhouse.finder.Repositories.PostRepository;
import com.jobhouse.finder.Repositories.RoleRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.CompanyProfile;
import com.jobhouse.finder.Tables.EmployeeProfile;
import com.jobhouse.finder.Tables.Post;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.Tables.CompanyProfile.Type;
import com.jobhouse.finder.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import antlr.collections.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@SessionAttributes("choose")
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepo;
    private CustomUserDetails userDetails;

    @GetMapping
    public String checkUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        this.userDetails = userDetails;
        return "redirect:/" + userDetails.getUsername();
    }

    @GetMapping("/{username}")
    public String homePage(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String username,
            Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user.getRole().getName().equals("Employee")) {
            EmployeeProfile employeeProfile = user.getEmployeeProfile();
            model.addAttribute("employee", employeeProfile);
            model.addAttribute("employeeProfile", new EmployeeProfile());
            model.addAttribute("userDetails", userDetails);

            Iterable<Post> posts = postRepo.findAll();
            model.addAttribute("posts", posts);
            return "employee";

        } else if (user.getRole().getName().equals("Company")) {
            Type[] types = Type.values();
            model.addAttribute("types", types);
            CompanyProfile companyProfile = user.getCompanyProfile();
            model.addAttribute("companyProfile", new CompanyProfile());
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("company", companyProfile);
            model.addAttribute("user", user);
            Iterable<Post> posts = postRepo.findByUserId(user.getId());
            model.addAttribute("posts", posts);
            return "Company";
        }
        Iterable<User> allUsers = userRepository.findAll();
        model.addAttribute("users", allUsers);
        model.addAttribute("userDetails", userDetails);
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "adminPage";
    }

    @GetMapping("/{username}/post/{id}")
    public String posts(Model model, @PathVariable String username, @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Post post = postRepo.findPostById(id);
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (post == null) {
            return "403";
        }
        // log.info(user.toString());
        boolean is_owner = (post.getUser().getId() == user.getId());
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("is_owner", is_owner);

        return "post";
    }

}
