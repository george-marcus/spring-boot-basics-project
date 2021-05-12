package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;

import com.udacity.jwdnd.course1.cloudstorage.viewModels.CredentialViewModel;
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

    public void saveCredential(final CredentialViewModel credentialViewModel, final String username) throws IOException {

        int userid = userService.getUser(username).getUserid();

        String key = encryptionService.generateNewKey();
        String encryptedPassword = encryptionService.encryptValue(credentialViewModel.getPassword(), key);

        Credential credential
             = new Credential(credentialViewModel.getCredentialId(), credentialViewModel.getUrl(), credentialViewModel.getUsername(),
                              key, encryptedPassword, userid);

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

    public List<CredentialViewModel> getAllCredentialsByUser(String userName) {

        int userid = userService.getUser(userName).getUserid();
        List<Credential> credentialList = credentialsMapper.getCredentialsByUserId(userid);
        List<CredentialViewModel> cFormList = new ArrayList<CredentialViewModel>();
        for(Credential credential : credentialList) {
            String decryptedPassword = encryptionService
                    .decryptValue(credential.getPassword(), credential.getKey());
            CredentialViewModel cForm = new CredentialViewModel(credential.getCredentialId(), credential.getUrl(),
                    credential.getUsername(), decryptedPassword, credential.getPassword());
            cFormList.add(cForm);
        }
        return cFormList;
    }

    public boolean isDuplicate(CredentialViewModel credentialViewModel, String username) {
        if(credentialViewModel.getUrl() == null || credentialViewModel.getUrl().isBlank() ||
                credentialViewModel.getUsername() == null || credentialViewModel.getUsername().isBlank() ||
                credentialViewModel.getPassword() == null || credentialViewModel.getPassword().isBlank())   {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return credentialsMapper.hasDuplicateUrl(credentialViewModel.getUrl(), credentialViewModel.getUsername(),
                credentialViewModel.getCredentialId(), userid);
    }
}
