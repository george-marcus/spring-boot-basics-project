package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.enums.ActiveTab;
import com.udacity.jwdnd.course1.cloudstorage.viewModels.CredentialViewModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class HomeController extends BaseController {

    @GetMapping("/home")
    public String getHomePage(Authentication authentication,
                              @ModelAttribute("noteModal") Note note,
                              @ModelAttribute("credentialModal") Credential credential,
                              Model model) {

        populateTabModelAttributes(model, authentication.getName(), ActiveTab.Files);
        return "home";
    }

    @GetMapping("/error")
    public String getError() {
        return "error";
    }
}