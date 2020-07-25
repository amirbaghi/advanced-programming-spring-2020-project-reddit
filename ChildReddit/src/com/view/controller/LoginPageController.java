package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginPageController {

    @FXML
    private ImageView redditIcon;

    @FXML
    private Label redditLabel;

    @FXML
    private ImageView searchIcon;

    @FXML
    private TextField serchTextField;

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
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    @FXML
    private Label incorrectLabel;

    public void initialize(){
        showPassword.setVisible(false);
        passwordShowField.setVisible(false);
        hidePassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showPassword());
        showPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hidePassword());

        loginButton.setOnAction(event -> {login();});

        signupButton.setOnAction(event -> {signup();});

        redditIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {homePage();});
        redditLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {homePage();});

        searchIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());
    }

    private void search(){
        String s = serchTextField.getText().trim();
        searchIcon.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(null,s),
                "Reddit - Search '" + s + "'");
    }

    private void showPassword(){
        hidePassword.setVisible(false);
        passwordShowField.setText(passwordField.getText());
        passwordShowField.setVisible(true);
        showPassword.setVisible(true);
        passwordField.setVisible(false);
    }

    private void hidePassword(){
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
            profilePage(user);
        }

    }

    private void signup(){
        signupButton.getScene().getWindow().hide();

        OpenWindow.openWindow("../SignupPage.fxml",new SignupPageController(), "ChildReddit - Signup Page");
    }

    private void homePage(){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml",new HomePageController(null), "ChildReddit - HomePage");
    }

    private void profilePage(User user){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml",new ProfilePageController(user, user), "ChildReddit - Profile Page " + user.getUserName());
    }

}
