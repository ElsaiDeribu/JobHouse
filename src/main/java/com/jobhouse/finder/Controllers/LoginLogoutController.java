package com.jobhouse.finder.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;

@Data
@Controller
public class LoginLogoutController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }
}
