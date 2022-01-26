package com.jobhouse.finder.Controllers;

import javax.validation.Valid;

import com.jobhouse.finder.Helpers.Choose;
import com.jobhouse.finder.Helpers.RegistrationForm;
import com.jobhouse.finder.Repositories.RoleRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.Role;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.security.DefaultUserService;
import com.jobhouse.finder.security.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
@SessionAttributes("choose")
public class RegistrationController {
    @Autowired
    private DefaultUserService userService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public final RoleRepository roleRepository;

    @GetMapping
    public String choose(Model model) {
        model.addAttribute("choose", new Choose());
        return "choose";
    }

    @GetMapping("/chosenEmployee")
    public String registerFormEmployee(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registrationEmployee";
    }

    @GetMapping("/chosenCompany")
    public String registerFormCompany(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registrationCompany";
    }

    @PostMapping
    public String processChoice(Choose ch) {
        if (ch.choice == null) {
            return "choose";
        }
        if (ch.choice.equals("employee")) {
            return "redirect:/register/chosenEmployee";
        } else {
            return "redirect:/register/chosenCompany";
        }
    }

    @PostMapping("/signup")
    public String processRegistration(@Valid RegistrationForm registrationForm, Errors error, Choose ch, Model model) {

        if (error.hasErrors()) {
            model.addAttribute("registrationForm", registrationForm);
            if (ch.choice.equals("employee")) {
                return "registrationEmployee";
            }
            return "registrationCompany";

        }
        try {
            User user = userService.register(registrationForm);
            if (ch.choice.equals("employee")) {
                Role role = roleRepository.findById(1);
                user.setRole(role);
            } else {
                Role role = roleRepository.findById(2);
                user.setRole(role);
            }
            log.info(user.toString());
            userRepository.save(user);
        } catch (UserAlreadyExistException e) {
            error.rejectValue("username", "registration.username", "An account already exists for this username.");
            error.rejectValue("email", "registration.email", "An account already exists for this email.");
            model.addAttribute("registrationForm", registrationForm);
            if (ch.choice.equals("employee")) {
                return "registrationEmployee";
            }
            return "registrationCompany";

        }
        return "redirect:/login";
    }

}
