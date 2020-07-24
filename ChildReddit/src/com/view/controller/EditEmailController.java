package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import com.model.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditEmailController {
    private User user;

    public EditEmailController(User user) {
        this.user = user;
    }

    @FXML
    private TextField emailField;

    @FXML
    private Label emailError;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton notSaveButton;

    @FXML
    public void initialize() {
        emailError.setText("");
        emailField.setText(user.getEmail());

        saveButton.setOnAction(event -> {save();});

        notSaveButton.setOnAction(event -> notSaveButton.getScene().getWindow().hide());

    }

    private void save(){
        String email = emailField.getText().trim();
        int valid = Validation.emailValidation(email);
        if(valid==0){
            emailError.setText("Invalid Email");
        }else {
            user.setEmail(email);
            saveButton.getScene().getWindow().hide();
        }
    }

}
