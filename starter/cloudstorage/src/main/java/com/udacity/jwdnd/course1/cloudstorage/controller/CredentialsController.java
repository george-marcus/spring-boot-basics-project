package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import com.udacity.jwdnd.course1.cloudstorage.dto.RedirectionModel;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.enums.ActiveTab;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CredentialsController extends BaseController {

    @Autowired
    private  CredentialsService credentialsService;

    @PostMapping("/save-credential")
    public String saveCredential(Authentication authentication,
                                     @ModelAttribute("noteModal") Note note,
                                     @ModelAttribute("credentialModal") Credential credential,
                                     Model model) {

        RedirectionModel redirectionModel = new RedirectionModel(model,true,
                "credentialSavedSuccess", authentication.getName(), ActiveTab.Credentials);

        if(credentialsService.isDuplicate(credential, authentication.getName())) {

             redirectionModel.setAttribute("credentialError");
             redirectionModel.setValue("Duplicate credential with the same username and url exists");
             return RedirectHomeWithValue(redirectionModel);
        }

        try {
            credentialsService.saveCredential(credential, authentication.getName());
            return RedirectHomeWithValue(redirectionModel);
        }
        catch(IOException e) {
            redirectionModel.setAttribute("credentialError");
            redirectionModel.setValue("Failed to save credential: " + e.getMessage());

            return RedirectHomeWithValue(redirectionModel);
        }
    }

    @GetMapping("/delete-credential")
    public String deleteCredential(Authentication authentication,
                                      @ModelAttribute("noteModal") Note note,
                                      @ModelAttribute("credentialModal") Credential credential,
                                      @RequestParam("credentialId") Integer credentialId,
                                      Model model) {

        RedirectionModel redirectionModel = new RedirectionModel(model,true,
                "credentialDeletedSuccess", authentication.getName(), ActiveTab.Credentials);

        credentialsService.deleteCredential(credentialId, authentication.getName());

        return RedirectHomeWithValue(redirectionModel);
    }

}