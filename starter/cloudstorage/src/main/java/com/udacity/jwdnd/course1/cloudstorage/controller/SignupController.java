package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignup() {
        return "signup";
    }

    @PostMapping
    public String postSignup(@ModelAttribute("user") User user, Model model) {

        if(userService.usernameExists(user.getUsername())) {
            model.addAttribute("signupError", "Username already exists");
            return "signup";
        }

        int createUserResult = userService.createUser((user));

        if(createUserResult > 0) {
            model.addAttribute("signupSuccess", true);
            return "login";
        }

        model.addAttribute("signupError", "Signup failed. Internal error.");
        return "signup";
    }

}