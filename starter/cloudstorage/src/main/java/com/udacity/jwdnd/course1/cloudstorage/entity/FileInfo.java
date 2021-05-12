package com.udacity.jwdnd.course1.cloudstorage.entity;

import java.io.InputStream;

public class FileInfo {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userid;
    private InputStream fileData;

    public FileInfo(Integer fileId, String fileName, Integer userid) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = null;
        this.fileSize = null;
        this.userid = userid;
        this.fileData = null;
    }

    public FileInfo(Integer fileId, String fileName, String contentType, String fileSize, Integer userid, InputStream fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userid = userid;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public InputStream getFileData() {
        return this.fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }
}
