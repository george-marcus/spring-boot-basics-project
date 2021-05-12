package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.RedirectionModel;
import com.udacity.jwdnd.course1.cloudstorage.enums.ActiveTab;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BaseController {
    @Autowired
    protected FileService fileService;

    @Autowired
    protected NotesService notesService;

    @Autowired
    protected CredentialsService credentialsService;

    protected String RedirectHomeWithValue(RedirectionModel redirectionModel) {
        Model model = redirectionModel.getModel();
        model.addAttribute(redirectionModel.getAttribute(), redirectionModel.getValue());
        populateTabModelAttributes(model, redirectionModel.getUsername(), redirectionModel.getActiveTab());

        return "home";
    }

    protected void populateTabModelAttributes(Model model, String username, ActiveTab activeTab) {
        model.addAttribute("files", fileService.getFilesByUser(username));
        model.addAttribute("notes", notesService.getNotesByUser(username));
        model.addAttribute("credentials", credentialsService.getAllCredentialsByUser(username));
        model.addAttribute("activeTab", activeTab.toString().toLowerCase());
    }
}
