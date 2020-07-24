package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import com.model.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePasswordController {
    private User user;

    public ChangePasswordController(User user) {
        this.user = user;
    }

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private JFXButton notSaveButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private Label incorrectLabel;
    @FXML
    void initialize() {

        saveButton.setOnAction(event -> {changePassword();});

        notSaveButton.setOnAction(event -> {notSaveButton.getScene().getWindow().hide();});

    }

    private void changePassword(){
        String oldPassword = oldPasswordField.getText().replaceFirst("\\s++$", "");
        String newPassword = newPasswordField.getText().replaceFirst("\\s++$", "");

        int valid = Validation.passwordValidation(newPassword);
        if(valid == 0){
            incorrectLabel.setText("Invalid password");
        }else if(valid == 2){
            incorrectLabel.setText("Length of your password must be between 6 and 16");
        }
        else{
            boolean valid1 = user.changePassword(oldPassword,newPassword);
            if(!valid1){
                incorrectLabel.setText("Incorrect Password");
            }
            else{
                saveButton.getScene().getWindow().hide();
            }
        }
    }
}
