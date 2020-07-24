package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ShortLoginController {
    private User user;
    public ShortLoginController(User user){this.user = user;}

    public User getUser(){return user;}

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label incorrectLabel;

    @FXML
    private JFXButton loginButton;

    @FXML
    public void initialize() {
        incorrectLabel.setText("");

        loginButton.setOnAction(event -> login());
    }

    private void login(){
        incorrectLabel.setText("");
        String username = usernameField.getText().replaceFirst("\\s++$", "");
        String password = passwordField.getText().replaceFirst("\\s++$", "");

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
