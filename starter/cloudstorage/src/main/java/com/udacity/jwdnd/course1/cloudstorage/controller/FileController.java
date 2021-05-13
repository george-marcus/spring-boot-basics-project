package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import com.udacity.jwdnd.course1.cloudstorage.dto.RedirectionModel;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.enums.ActiveTab;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @PostMapping("/file-upload")
    public String postFileUpload(Authentication authentication,
                                 @ModelAttribute("noteModal") Note note,
                                 @ModelAttribute("credentialModal") Credential credential,
                                 @RequestParam("fileUpload") MultipartFile fileUpload,
                                 Model model) {

        RedirectionModel redirectionModel = new RedirectionModel(model,true,
                "uploadSuccess", authentication.getName(), ActiveTab.Files);

        double fileSizeInMB = fileUpload.getSize() * 0.00000095367432;

        if(fileSizeInMB > 8){
            redirectionModel.setAttribute("uploadError");
            redirectionModel.setValue("File Size is too large");

            return RedirectHomeWithValue(redirectionModel);
        }

        if(fileUpload.getOriginalFilename() == null || fileUpload.getOriginalFilename().isBlank() || fileUpload.isEmpty()) {

            redirectionModel.setAttribute("uploadError");
            redirectionModel.setValue("Please select a file!");

            return RedirectHomeWithValue(redirectionModel);
        }

        if(fileService.isDuplicate(fileUpload.getOriginalFilename(), authentication.getName())) {

            redirectionModel.setAttribute("uploadError");
            redirectionModel.setValue("File with this name is already uploaded.");

            return RedirectHomeWithValue(redirectionModel);
        }

        try {
            fileService.uploadFile(fileUpload, authentication.getName());
            return RedirectHomeWithValue(redirectionModel);
        }
        catch(IOException e) {

            redirectionModel.setAttribute("uploadError");
            redirectionModel.setValue("File upload failed: " + e.getMessage());

            return RedirectHomeWithValue(redirectionModel);
        }

    }


    @GetMapping("/file-delete")
    public String deleteFile(Authentication authentication,
                                @ModelAttribute("noteModal") Note note,
                                @ModelAttribute("credentialModal") Credential credential,
                                @RequestParam("fileId") Integer fileId, Model model) {

        RedirectionModel redirectionModel = new RedirectionModel(model,true,
                "fileDeletedSuccess", authentication.getName(), ActiveTab.Files);

        fileService.deleteFile(fileId, authentication.getName());;

        return RedirectHomeWithValue(redirectionModel);
    }


    @GetMapping("/file-download")
    public ResponseEntity<byte[]> downloadFile(Authentication authentication,
                                               @ModelAttribute("noteModal") Note note,
                                               @ModelAttribute("credentialModal") Credential credential,
                                               @RequestParam("fileId") Integer fileId) throws IOException {

        FileInfo file = fileService.downloadFile(fileId, authentication.getName());

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
            .body(file.getFileData().readAllBytes());
    }
}