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
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signup(@ModelAttribute User user, Model model) {
        String error = null;

        if(!userService.isUsernameAvailable(user.getUsername())) {
            error = "This username has already been taken";
        }

        if(error == null) {
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 1) {
                error = "An error occurred trying to sign you up. Please try again";
            }
        }

        if(error == null) {
            model.addAttribute("signupSuccess", true);
        }else {
            model.addAttribute("signupError", error);
        }

        return "signup";
    }
}
