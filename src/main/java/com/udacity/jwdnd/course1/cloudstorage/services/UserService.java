package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;
    public User user;

    public UserService(UserMapper userMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public Boolean isUsernameAvailable(String username) {
        return userMapper.findUser(username) == null;
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(user.getPassword(), encodedKey);

        return userMapper.insert(new User(null, user.getUsername(), encodedKey,
                encryptedPassword, user.getFirstname(), user.getLastname()));
    }

    public User getUser(String username) {
        return userMapper.findUser(username);
    }

    public Authentication currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public int currentUserId() {
        return getUser(currentUser().getName()).getUserid();
    }
}
