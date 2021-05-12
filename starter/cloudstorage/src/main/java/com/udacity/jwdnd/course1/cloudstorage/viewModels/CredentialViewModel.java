package com.udacity.jwdnd.course1.cloudstorage.viewModels;

public class CredentialViewModel {

    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private String encryptedPassword;


    public CredentialViewModel(Integer credentialId, String url, String username, String password, String encryptedPassword) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getCredentialId() {
        return this.credentialId;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
