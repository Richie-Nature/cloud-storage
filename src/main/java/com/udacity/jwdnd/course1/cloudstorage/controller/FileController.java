package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/{fname}")
    public void getFile(@PathVariable("fname") String fileName,HttpServletResponse resp) throws IOException {
        int userId = userService.currentUserId();
        File file = fileService.getFile(fileName, userId);
        byte[] fileData = file.getFiledata();

        resp.setContentType(file.getContenttype());
        resp.setHeader("Content-Disposition", "attachment; filename="+ file.getFilename());
        resp.setContentLength(fileData.length);

        OutputStream out = resp.getOutputStream();
        try {
            out.write(fileData);
            out.flush();
        }finally {
            out.close();
        }
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
           int rows = fileService.createFile(file);
           if(rows > 0)
            redirectAttributes.addFlashAttribute("success",
                    file.getOriginalFilename()+" uploaded successfully");
        }catch (StorageException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}/{name}")
    public String deleteFile(@PathVariable("id") int fileId,
                             @PathVariable("name") String name,
                             RedirectAttributes redirectAttributes) {
        int userId = userService.currentUserId();
        if(fileService.deleteFile(fileId, userId) < 1) {
         redirectAttributes.addFlashAttribute("error",
                 "Could not delete file. Please try again");
        } else {
            redirectAttributes.addFlashAttribute("success",
                    name + " deleted successfully!");
        }
        return "redirect:/home";
    }

}
