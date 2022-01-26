package com.jobhouse.finder.Controllers;

import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.CompanyProfile;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/companyDashboard")
public class CompanyDashboardController {
    @Autowired
    private UserRepository repo;

    @GetMapping
    public String handleDashboard(Model model) {
        CustomUserDetails c = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = repo.findByUsername(c.getUsername());
        CompanyProfile cmp = user.getCompanyProfile();
        model.addAttribute("companyProfile", cmp);
        model.addAttribute("user", user);
        return "companyDashboard";
    }
}
