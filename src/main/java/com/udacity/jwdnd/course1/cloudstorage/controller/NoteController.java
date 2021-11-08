package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();

        int rowsAdded = noteService.createNote(note, userId);

        if(rowsAdded < 1) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to add note. Please try again");
        }
        return "redirect:/home#nav-notes";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();

        int rowsAdded = noteService.updateNote(note, userId);

        if(rowsAdded < 1) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update note. Please try again");
        }
        return "redirect:/home#nav-notes";
    }

    @GetMapping("/remove{id}")
    public String removeNote(@PathVariable("id")int id,
                             RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();
        if(noteService.deleteNote(id, userId) < 1) {
            redirectAttributes.addFlashAttribute("error",
                    "Could not delete note. Please try again");
        }
        return "redirect:/home#nav-notes";
    }
}
