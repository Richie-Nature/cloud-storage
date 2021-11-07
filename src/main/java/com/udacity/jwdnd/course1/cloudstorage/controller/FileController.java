package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fname}")
    public void getFile(@PathVariable("fname") String fileName, HttpServletResponse resp) throws IOException {
        File file = fileService.getFile(fileName);
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
    public String uploadFile(@RequestParam("fileUpload")MultipartFile file) {
        fileService.createFile(file);
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") String fileId, Model model) {
        fileService.deleteFile(Integer.parseInt(fileId));
        return "redirect:/home";
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleException(StorageException exc) {
        return ResponseEntity.notFound().build();
    }

}
