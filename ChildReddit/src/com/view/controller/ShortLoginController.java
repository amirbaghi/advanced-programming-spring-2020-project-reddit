package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ShortLoginController {
    private User user;
    public ShortLoginController(User user){this.user = user;}

    public User getUser(){return user;}

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordShowField;

    @FXML
    private ImageView showPassword;

    @FXML
    private ImageView hidePassword;


    @FXML
    private Label incorrectLabel;

    @FXML
    private JFXButton loginButton;

    @FXML
    public void initialize() {

        showPassword.setVisible(false);
        passwordShowField.setVisible(false);
        hidePassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showPassword());
        showPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hidePassword());
        incorrectLabel.setText("");

        loginButton.setOnAction(event -> login());
    }

    private void showPassword(){
        hidePassword.setVisible(false);
        passwordShowField.setText(passwordField.getText());
        passwordShowField.setVisible(true);
        showPassword.setVisible(true);
        passwordField.setVisible(false);
    }

    private void hidePassword() {
        showPassword.setVisible(false);
        hidePassword.setVisible(true);
        passwordField.setText(passwordShowField.getText());
        passwordField.setVisible(true);
        passwordShowField.setVisible(false);
    }

    private void login(){
        incorrectLabel.setText("");
        String username = usernameField.getText().replaceFirst("\\s++$", "");
        String password;
        if(hidePassword.isVisible()){
            password = passwordField.getText().replaceFirst("\\s++$", "");
        }else{
            password = passwordShowField.getText().replaceFirst("\\s++$", "");
        }
        User user = User.login(username, password);
        if(user == null){
            incorrectLabel.setText("Incorrect username or password");
        }
        else{
            this.user = user;
            loginButton.getScene().getWindow().hide();
        }
    }

}
