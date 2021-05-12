package com.udacity.jwdnd.course1.cloudstorage.dto;

import com.udacity.jwdnd.course1.cloudstorage.enums.ActiveTab;
import org.springframework.ui.Model;

public class RedirectionModel {
    private Model model;
    private Object value;
    private ActiveTab activeTab;
    private String attribute;
    private String username;

    public RedirectionModel(Model model, Object value, String attribute, String username, ActiveTab activeTab){
        this.model = model;
        this.value = value;
        this.attribute = attribute;
        this.username = username;
        this.activeTab = activeTab;
    }
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ActiveTab getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(ActiveTab activeTab) {
        this.activeTab = activeTab;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
