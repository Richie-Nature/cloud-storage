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

    @PostMapping()
    public String saveNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();
        int rowsAdded = 0;
        String errorMessage = null;
        String successMessage = null;

        if(note.getNoteid() != null) {
            rowsAdded = noteService.updateNote(note, userId);
            errorMessage = "Error updating note. Please try again";
            successMessage = note.getNotetitle() + " updated successfully!";
        } else {
            rowsAdded = noteService.createNote(note, userId);
            errorMessage = "Error adding note. Please try again";
            successMessage = note.getNotetitle() + " added successfully!";
        }

        if(rowsAdded < 1) {
            redirectAttributes.addFlashAttribute("error",errorMessage);
        }else {
            redirectAttributes.addFlashAttribute("success",successMessage);
        }
        return "redirect:/home#nav-notes";
    }

    @GetMapping("/remove/{id}/{name}")
    public String removeNote(@PathVariable("id")int id,
                             @PathVariable("name")String name,
                             RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();
        if(noteService.deleteNote(id, userId) < 1) {
            redirectAttributes.addFlashAttribute("error",
                    "Could not delete note. Please try again");
        } else {
            redirectAttributes.addFlashAttribute("success",
                    name + " deleted successfully!");
        }
        return "redirect:/home#nav-notes";
    }
}
