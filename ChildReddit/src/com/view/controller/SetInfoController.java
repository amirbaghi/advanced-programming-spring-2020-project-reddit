package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.SubReddit;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class SetInfoController {
    private User user;
    private SubReddit subReddit;

    public SetInfoController(User user){
        this.user = user;
    }
    public SetInfoController(SubReddit subReddit){ this.subReddit = subReddit; }

    @FXML
    private TextArea infoTextArea;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton notSaveButton;

    @FXML
    public void initialize() {
        if(user != null) {
            infoTextArea.setText(user.getInfo());
        }
        else{
            infoTextArea.setText(subReddit.getDescription());
        }

        saveButton.setOnAction(event -> {save();});

        notSaveButton.setOnAction(event -> notSaveButton.getScene().getWindow().hide());

    }

    private void save(){
        if(user!= null) {
            user.setInfo(infoTextArea.getText().replaceFirst("\\s++$", ""));
        }
        else{
            subReddit.setDescription(infoTextArea.getText().replaceFirst("\\s++$", ""));
        }
        saveButton.getScene().getWindow().hide();
    }
}
