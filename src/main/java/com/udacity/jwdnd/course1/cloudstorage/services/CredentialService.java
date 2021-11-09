package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Stream;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final KeyGeneratorService keyService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService,
                             KeyGeneratorService keyService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.keyService = keyService;
    }

    public int createCredential(Credential credential, int userId) {
        String encodedKey = keyService.encryptionKey();
        String encryptedPassword = encryptPassword(credential.getPassword(),encodedKey);
        return credentialMapper.insert(new Credential(null,
                credential.getUrl(), credential.getUsername(),
                encodedKey,encryptedPassword, userId));
    }

    public Stream<Credential> getCredentials(int userId) {
        return credentialMapper.findAll(userId).stream();
    }

    public Credential getCredential(int noteId, int userId) {
        return credentialMapper.findById(noteId, userId);
    }

    public int updateCredential(Credential credential, int userId) {
        String encodedKey = keyService.encryptionKey();
        String encryptedPassword = encryptPassword(credential.getPassword(),encodedKey);
        return credentialMapper.update(credential.getUrl(),
                credential.getUsername(),
                encodedKey,
                encryptedPassword,
                credential.getCredentialid(), userId);
    }

    public int deleteCredential(int credentialId, int userId) {
        return credentialMapper.delete(credentialId, userId);
    }

    private String encryptPassword(String password, String key) {
        return encryptionService.encryptValue(password,key);
    }
}
