package com.jobhouse.finder.Controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.jobhouse.finder.security.CustomUserDetails;
import com.jobhouse.finder.Helpers.FileUploadUtil;
import com.jobhouse.finder.Repositories.CompanyProfileRepository;
import com.jobhouse.finder.Repositories.RoleRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.CompanyProfile;
import com.jobhouse.finder.Tables.Role;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.Tables.CompanyProfile.Type;

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
public class CompanyProfileController {
    @Autowired
    private CompanyProfileRepository companyProfileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/{username}/EditCompany")
    public String editProfile(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        CompanyProfile companyProfile = user.getCompanyProfile();
        model.addAttribute("companyProfile", new CompanyProfile());
        model.addAttribute("userDetails", new CustomUserDetails(user));
        model.addAttribute("company", companyProfile);
        Type[] types = Type.values();
        model.addAttribute("types", types);
        return "EditCompanyProfile";
    }

    @PostMapping("/{username}/EditCompany")
    public String setProfile(@PathVariable String username, @Valid CompanyProfile cmp, Errors errors,
            @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {

        User user = userRepository.findByUsername(username);
        CompanyProfile companyProfile = user.getCompanyProfile();
        if (errors.hasErrors()) {
            model.addAttribute("companyProfile", cmp);
            model.addAttribute("userDetails", new CustomUserDetails(user));
            model.addAttribute("company", companyProfile);
            Type[] types = Type.values();
            model.addAttribute("types", types);
            return "EditCompanyProfile";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (companyProfile != null) {
            companyProfile.setLocation(cmp.getLocation());
            companyProfile.setType(cmp.getType());
            companyProfile.setLogo(fileName);
            companyProfile.setUser(user);
            CompanyProfile savedCompanyProfile = companyProfileRepository.save(companyProfile);
            String uploadDir = "user-photos/" + savedCompanyProfile.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            cmp.setLogo(fileName);
            user.setCompanyProfile(cmp);
            CompanyProfile savedCompanyProfile = userRepository.save(user).getCompanyProfile();
            String uploadDir = "user-photos/" + savedCompanyProfile.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/" + user.getUsername();
    }

    @GetMapping("/{username}/EditCompany/deleteProfile")
    public String deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        user.setRole(null);
        userRepository.save(user);
        CompanyProfile companyProfile = user.getCompanyProfile();
        companyProfileRepository.delete(companyProfile);
        userRepository.delete(user);
        return "redirect:/login";
    }

}
