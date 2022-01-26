package com.jobhouse.finder.Controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.jobhouse.finder.Helpers.FileUploadUtil;
import com.jobhouse.finder.Repositories.EmployeeProfileRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.EmployeeProfile;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
public class EmployeeProfileController {
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}/EditEmployee")
    public String editProfile(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        EmployeeProfile employeeProfile = user.getEmployeeProfile();
        model.addAttribute("employeeProfile", new EmployeeProfile());
        model.addAttribute("userDetails", new CustomUserDetails(user));
        model.addAttribute("employee", employeeProfile);
        return "EditEmployeeProfile";
    }

    @PostMapping("/{username}/EditEmployee")
    public String setProfile(@PathVariable String username, @Valid EmployeeProfile emp, Errors errors,
            @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {

        User user = userRepository.findByUsername(username);
        EmployeeProfile employeeProfile = user.getEmployeeProfile();
        if (errors.hasErrors()) {
            model.addAttribute("employeeProfile", emp);
            model.addAttribute("userDetails", new CustomUserDetails(user));
            model.addAttribute("employee", employeeProfile);
            return "EditEmployeeProfile";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (employeeProfile != null) {
            employeeProfile.setFieldOfStudy(emp.getFieldOfStudy());
            employeeProfile.setGPA(emp.getGPA());
            employeeProfile.setEducationLevel(emp.getEducationLevel());
            employeeProfile.setYearsOfExperience(emp.getYearsOfExperience());
            employeeProfile.setBio(emp.getBio());
            employeeProfile.setLocation(emp.getLocation());

            employeeProfile.setProfilePicture(fileName);
            employeeProfile.setUser(user);
            EmployeeProfile savedEmployeeProfile = employeeProfileRepository.save(employeeProfile);
            String uploadDir = "user-photos/" + savedEmployeeProfile.getUser().getUsername();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            emp.setProfilePicture(fileName);
            user.setEmployeeProfile(emp);
            userRepository.save(user).getEmployeeProfile();
            String uploadDir = "user-photos/" + user.getUsername();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/" + user.getUsername();
    }

    @GetMapping("/{username}/EditEmployee/deleteProfile")
    public String deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        user.setRole(null);
        userRepository.save(user);
        EmployeeProfile employeeProfile = user.getEmployeeProfile();
        employeeProfileRepository.delete(employeeProfile);
        userRepository.delete(user);
        return "redirect:/login";
    }
}
