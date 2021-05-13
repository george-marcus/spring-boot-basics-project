package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import com.udacity.jwdnd.course1.cloudstorage.dto.RedirectionModel;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.enums.ActiveTab;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController extends BaseController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/save-note")
    public String postNoteSave(Authentication authentication,
                               @ModelAttribute("noteModal") Note note,
                               @ModelAttribute("credentialModal") Credential credential,
                               Model model) {

        RedirectionModel redirectionModel = new RedirectionModel(model,true,
                "noteSavedSuccess", authentication.getName(), ActiveTab.Notes);

        if(notesService.isDuplicate(note, authentication.getName())) {

            redirectionModel.setAttribute("noteError");
            redirectionModel.setValue("Duplicate note with the same title exists");

            return RedirectHomeWithValue(redirectionModel);
        }

        try {
            notesService.saveNote(note, authentication.getName());
            return  RedirectHomeWithValue(redirectionModel);
        }
        catch(IOException e) {
            redirectionModel.setAttribute("noteError");
            redirectionModel.setValue("Saving note failed: " + e.getMessage());

            return  RedirectHomeWithValue(redirectionModel);
        }
    }

    @GetMapping("/delete-note")
    public String getNoteDelete(Authentication authentication,
                                @ModelAttribute("noteModal") Note note,
                                @ModelAttribute("credentialModal") Credential credential,
                                @RequestParam("noteId") Integer noteId,
                                Model model) {

        RedirectionModel redirectionModel = new RedirectionModel(model,true,
                "noteDeletedSuccess", authentication.getName(), ActiveTab.Notes);

            notesService.deleteNote(noteId);
            return  RedirectHomeWithValue(redirectionModel);

    }

}