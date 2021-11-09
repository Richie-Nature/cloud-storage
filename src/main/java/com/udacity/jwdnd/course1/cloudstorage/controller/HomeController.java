package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(FileService fileService, NoteService noteService,
                          UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String initView(Model model) {
        int userId = userService.currentUserId();
        model.addAttribute("files",
                fileService.getAllFiles(userId).collect(Collectors.toList()));
        model.addAttribute("notes",
                noteService.getAllNotes(userId).collect(Collectors.toList()));
        return "home";
    }
}
