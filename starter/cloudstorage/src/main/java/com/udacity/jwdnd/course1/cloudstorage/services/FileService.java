package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.entity.FileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserService userService;

    public boolean isDuplicate(String filePath, String username) {

        if(filePath == null || filePath.isBlank()) {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return fileMapper.isDuplicateFile(filePath, userid);
    }

    public void uploadFile(final MultipartFile fileUpload, final String username) throws IOException {

        int userid = userService.getUser(username).getUserid();

        try (InputStream fis = fileUpload.getInputStream()) {

            FileInfo file = new FileInfo(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                    Long.toString(fileUpload.getSize()), userid, fis);

            int fileId = fileMapper.insert(file);

            if (fileId < 1) {
                throw new IOException("Failed to insert file data to database");
            }
        }
    }

    public FileInfo downloadFile(final Integer fileId, final String username) {

        Integer userid = userService.getUser(username).getUserid();

        return fileMapper.getFileByUser(fileId, userid);
    }

    public void deleteFile(final Integer fileId, final String username) {

        int userid = userService.getUser(username).getUserid();

        fileMapper.deleteByUser(fileId, userid);
    }

    public List<FileInfo> getFilesByUser(String userName) {
        int userid = userService.getUser(userName).getUserid();
        return fileMapper.getFilesByUser(userid);
    }
}
