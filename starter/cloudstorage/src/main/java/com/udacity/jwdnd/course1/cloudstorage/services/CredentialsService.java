package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsMapper credentialsMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionService encryptionService;

    public void saveCredential(final Credential credential, final String username) throws IOException {

        int userid = userService.getUser(username).getUserid();
        credential.setUserid(userid);

        String key = encryptionService.generateNewKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);

        credential.setPassword(encryptedPassword);
        credential.setKey(key);

        int credentialId = 0;

        if(credential.getCredentialId() == null) {
            credentialId = credentialsMapper.insert(credential);
        }
        else {
            credentialId = credentialsMapper.update(credential);
        }

        if(credentialId < 1) {
            throw new IOException("Failed to insert/update Credential");
        }
    }

    public void deleteCredential(final Integer credentialId, final String username) {
        int userid = userService.getUser(username).getUserid();
        credentialsMapper.delete(credentialId, userid);
    }

    public List<Credential> getAllCredentialsByUser(String userName) {

        int userid = userService.getUser(userName).getUserid();
        List<Credential> credentialList = credentialsMapper.getCredentialsByUserId(userid);
        for(Credential credential : credentialList) {

            String decryptedPassword = encryptionService
                    .decryptValue(credential.getPassword(), credential.getKey());

            credential.setDecryptedPassword(decryptedPassword);
        }
        return credentialList;
    }

    public boolean isDuplicate(Credential credential, String username) {
        if(credential.getUrl() == null || credential.getUrl().isBlank() ||
                credential.getUsername() == null || credential.getUsername().isBlank() ||
                credential.getPassword() == null || credential.getPassword().isBlank())   {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return credentialsMapper.hasDuplicateUrl(credential.getUrl(), credential.getUsername(),
                credential.getCredentialId(), userid);
    }
}
