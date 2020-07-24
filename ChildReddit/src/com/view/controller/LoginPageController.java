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
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    @FXML
    private Label incorrectLabel;

    public void initialize(){
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

    private void login(){
        incorrectLabel.setText("");
        String username = usernameField.getText().replaceFirst("\\s++$", "");
        String password = passwordField.getText().replaceFirst("\\s++$", "");

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

        OpenWindow.openWindow("../SignupPage.fxml",new SignupPageController(), "Reddit - Signup Page");
    }

    private void homePage(){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml",new HomePageController(null), "Reddit - HomePage");
    }

    private void profilePage(User user){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml",new ProfilePageController(user, user), "Reddit - Profile Page " + user.getUserName());
    }

}
