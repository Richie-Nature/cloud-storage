package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping()
    public String saveCredentials(@ModelAttribute Credential credential,
                                  RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();
        int rowsAdded = 0;
        String errorMessage = null;
        String successMessage = null;

        if(credential.getCredentialid() != null) {
            rowsAdded = credentialService.updateCredential(credential, userId);
            errorMessage = "Error updating credential. Please try again";
            successMessage = credential.getUrl() + " updated successfully!";
        } else {
            rowsAdded = credentialService.createCredential(credential, userId);
            errorMessage = "Error adding credential. Please try again";
            successMessage = credential.getUrl() + " added successfully!";
        }

        if(rowsAdded < 1) {
            redirectAttributes.addFlashAttribute("error",errorMessage);
        }else {
            redirectAttributes.addFlashAttribute("success",successMessage);
        }
        return "redirect:/home#nav-credentials";
    }

    @GetMapping("/remove/{id}")
    public String removeCredential(@PathVariable("id")int id,
                             RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();
        if(credentialService.deleteCredential(id, userId) < 1) {
            redirectAttributes.addFlashAttribute("error",
                    "Could not delete credential. Please try again");
        } else {
            redirectAttributes.addFlashAttribute("success",
                    "Credential deleted successfully!");
        }
        return "redirect:/home#nav-credentials";
    }
}
