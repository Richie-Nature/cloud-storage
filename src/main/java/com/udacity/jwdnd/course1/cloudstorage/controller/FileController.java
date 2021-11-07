package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fname}/{userid}")
    public void getFile(@PathVariable("fname") String fileName,
                        @PathVariable("userid") int userid,HttpServletResponse resp) throws IOException {
        File file = fileService.getFile(fileName, userid);
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
            fileService.createFile(file);
        }catch (StorageException e) {
            redirectAttributes.addFlashAttribute("fileError", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}/{userid}")
    public String deleteFile(@PathVariable("id") int fileId,
                             @PathVariable("userid") int userId, Model model) {
        fileService.deleteFile(fileId, userId);
        return "redirect:/home";
    }

}
