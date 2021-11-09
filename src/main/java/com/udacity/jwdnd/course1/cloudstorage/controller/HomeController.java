package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService,
                          UserService userService, CredentialService credentialService,
                          EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String initView(Model model) {
        int userId = userService.currentUserId();
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("files",fileService.getAllFiles(userId).collect(Collectors.toList()));
        modelMap.put("notes",noteService.getAllNotes(userId).collect(Collectors.toList()));
        modelMap.put("credentials",credentialService.getCredentials(userId).collect(Collectors.toList()));
        modelMap.put("encryptionService", encryptionService);

        model.addAllAttributes(modelMap);
        return "home";
    }
}
