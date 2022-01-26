package com.jobhouse.finder.Controllers;

import javax.validation.Valid;

import com.jobhouse.finder.Helpers.ImageConverter;
import com.jobhouse.finder.Repositories.PostRepository;
import com.jobhouse.finder.Repositories.UserRepository;
import com.jobhouse.finder.Tables.Post;
import com.jobhouse.finder.Tables.User;
import com.jobhouse.finder.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String postForm(Model model) {

        model.addAttribute("is_create", true);
        model.addAttribute("post", new Post());

        return "post_form";
    }

    @PostMapping
    public String processPost(
            @Valid Post post,
            Errors errors,
            @RequestParam MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails user,
            Model model) throws Exception {

        if (errors.hasErrors()) {
            model.addAttribute("is_create", true);
            return "post_form";
        }
        User use = userRepo.findByUsername(user.getUsername());
        post.setUser(use);
        post.setImage(new ImageConverter().convert(image));

        postRepo.save(post);
        return "redirect:/" + user.getUsername() + "/post/" + post.getId();
    }

    // Edit post
    @GetMapping("/edit/{id}")
    public String editPostForm(Model model, @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userRepo.findByUsername(userDetails.getUsername());
        Post post = postRepo.findPostById(id);

        if (post != null && (post.getUser().getId() == user.getId() || user.isAdmin())) {

            model.addAttribute("post", post);
            model.addAttribute("is_create", false);

            return "post_form";
        }

        return "403";
    }

    @PostMapping("/edit/{id}")
    public String editPost(Model model,
            @PathVariable long id,
            @Valid Post Post,
            Errors errors,
            @RequestParam MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {

        User user = userRepo.findByUsername(userDetails.getUsername());
        Post post = postRepo.findPostById(id);

        if (errors.hasErrors()) {
            model.addAttribute("is_create", false);
            return "post_form";
        }

        post.setImage(new ImageConverter().convert(image));

        postRepo.save(post);

        return "redirect:/" + user.getUsername() + "/post/" + post.getId();
    }

    // Delete post
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername());
        Post post = postRepo.findPostById(id);

        if (post != null && (post.getUser().getId() == user.getId() || user.isAdmin())) {
            postRepo.delete(post);
        }

        return "redirect:/";
    }

}
