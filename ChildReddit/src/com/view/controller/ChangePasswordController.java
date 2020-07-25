package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import com.model.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private TextField oldPasswordShowField;

    @FXML
    private TextField newPasswordShowField;

    @FXML
    private ImageView showOldPassword;

    @FXML
    private ImageView showNewPassword;

    @FXML
    private ImageView hideOldPassword;

    @FXML
    private ImageView hideNewPassword;


    @FXML
    void initialize() {

        oldPasswordShowField.setVisible(false);
        showOldPassword.setVisible(false);
        newPasswordShowField.setVisible(false);
        showNewPassword.setVisible(false);

        hideOldPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showOLdPassword());
        hideNewPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showNewPassword());

        showOldPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hideOldPassword());
        showNewPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hideNewPassword());

        saveButton.setOnAction(event -> {changePassword();});

        notSaveButton.setOnAction(event -> {notSaveButton.getScene().getWindow().hide();});

    }

    private void showOLdPassword(){
        hideOldPassword.setVisible(false);
        oldPasswordShowField.setText(oldPasswordField.getText());
        oldPasswordShowField.setVisible(true);
        showOldPassword.setVisible(true);
        oldPasswordField.setVisible(false);
    }

    private void showNewPassword() {
        hideNewPassword.setVisible(false);
        showNewPassword.setVisible(true);
        newPasswordShowField.setText(newPasswordField.getText());
        newPasswordShowField.setVisible(true);
        newPasswordField.setVisible(false);
    }

    private void hideOldPassword(){
        showOldPassword.setVisible(false);
        oldPasswordField.setText(oldPasswordShowField.getText());
        oldPasswordField.setVisible(true);
        hideOldPassword.setVisible(true);
        oldPasswordShowField.setVisible(false);
    }

    private void hideNewPassword() {
        showNewPassword.setVisible(false);
        hideNewPassword.setVisible(true);
        newPasswordField.setText(newPasswordShowField.getText());
        newPasswordField.setVisible(true);
        newPasswordShowField.setVisible(false);
    }

    private void changePassword(){
        incorrectLabel.setText("");
        String oldPassword;
        if(hideOldPassword.isVisible()){
            oldPassword = oldPasswordField.getText().replaceFirst("\\s++$", "");;
        }else{
            oldPassword = oldPasswordShowField.getText().replaceFirst("\\s++$", "");
        }

        String newPassword;
        if(hideNewPassword.isVisible()){
            newPassword = newPasswordField.getText().replaceFirst("\\s++$", "");
        }else{
            newPassword = newPasswordShowField.getText().replaceFirst("\\s++$", "");
        }

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
