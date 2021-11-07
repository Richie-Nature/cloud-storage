package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public int createFile(MultipartFile file) {
        int userId = userService.getUser(userService.currentUser().getName()).getUserid();
        try {
            if(file.isEmpty()) {
                throw new StorageException("Please choose a file to upload");
            }
            if(getFile(file.getOriginalFilename(), userId) != null) {
                throw new StorageException("File already exists!");
            } else {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                return fileMapper.insert(new File(null, fileName, file.getContentType(),
                        file.getSize(), userId, file.getBytes()));
            }

        }
        catch (IOException e) {
            throw new StorageException("File upload failed.", e);
        }

    }

    public Stream<File> getAllFiles() {
        int userId = userService.getUser(userService.currentUser().getName()).getUserid();
        return fileMapper.findAll(userId).stream();
    }

    public File getFile(int id) {
        return fileMapper.findById(id);
    }

    public File getFile(String name, int userId) {
        return fileMapper.findByName(name, userId);
    }

    public int deleteFile(int id, int userId) {
        return fileMapper.delete(id, userId);
    }
}
