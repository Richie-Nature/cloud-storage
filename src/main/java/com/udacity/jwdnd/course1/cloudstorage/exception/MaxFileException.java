package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class MaxFileException {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxException(MaxUploadSizeExceededException e,
                                     RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "File cannot exceed 5mb");
        return "redirect:/home";
    }
}
