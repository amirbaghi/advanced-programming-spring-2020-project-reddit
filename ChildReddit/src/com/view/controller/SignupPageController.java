package com.view.controller;

import com.exception.UserExistException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.model.Gender;
import com.model.User;
import com.model.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SignupPageController {

    @FXML
    private TextField searchTextBox;

    @FXML
    private ImageView search;

    @FXML
    private ImageView redditIcon;

    @FXML
    private Label redditLabel;

    @FXML
    private JFXButton loginButton;

    @FXML
    private TextField usernameBox;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private JFXComboBox<Gender> genderBox;

    @FXML
    private TextField emailBox;

    @FXML
    private TextField ageBox;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    private Label genderError;

    @FXML
    private Label emailError;

    @FXML
    private Label ageError;

    @FXML
    private JFXCheckBox privacyBox;

    @FXML
    private Label privacyError;

    @FXML
    private JFXButton signupButton;

    @FXML
    public void initialize(){
        ObservableList<Gender> genders = FXCollections.observableArrayList(Gender.values());
        genderBox.setItems(genders);

        signupButton.setOnAction(event -> { signUp(); });

        loginButton.setOnAction(event -> {login();});

        redditIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {homePage();});
        redditLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {homePage();});

        search.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());
    }

    private void search(){
        String s = searchTextBox.getText().trim();
        search.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(null, s),
                "Reddit - Search '" + s + "'");
    }

    private void signUp(){
        int valid = 1;
        usernameError.setText("");
        passwordError.setText("");
        genderError.setText("");
        emailError.setText("");
        ageError.setText("");
        privacyError.setText("");

        String username = usernameBox.getText().replaceFirst("\\s++$", "");
        String password = passwordBox.getText().replaceFirst("\\s++$", "");
        Gender gender = genderBox.getSelectionModel().getSelectedItem();
        String email = emailBox.getText().trim();
        String strAge = ageBox.getText().trim();

        int validUsername = Validation.usernameValidation(username);
        if(validUsername == 0){
            usernameError.setText("Invalid username");
            valid = 0;
        }else if(validUsername == 2){
            usernameError.setText("Length of username must be between 3 and 12");
            valid = 0;
        }

        int validPassword = Validation.passwordValidation(password);
        if(validPassword == 0){
            passwordError.setText("Invalid password");
            valid = 0;
        }else if(validPassword == 2){
            passwordError.setText("Length of your password must be between 6 and 16");
            valid = 0;
        }

        if(gender == null){
            genderError.setText("Gender must be choose");
            valid = 0;
        }

        int validAge = Validation.ageValidation(strAge);
        if(validAge==0){
            ageError.setText("Invalid Age");
            valid = 0;
        }else if(validAge == 2){
            ageError.setText("You not allow to use this application");
            valid = 0;
        }

        int validEmail = Validation.emailValidation(email);
        if(validEmail==0){
            emailError.setText("Invalid Email");
            valid = 0;
        }

        if(!privacyBox.isSelected()){
            privacyError.setText("You must agree with our privacy and policy");
            valid = 0;
        }

        if(valid == 1) {
            try {
                User user = User.signUp(username, password, gender);
                if(strAge!=null){
                    user.setAge(strAge);
                }
                if(email!=null){
                    user.setEmail(email);
                }
                profilePage(user);
            } catch (UserExistException e) {
                usernameError.setText("This username before used");
            }
        }

    }

    private void profilePage(User user){
        signupButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml",new ProfilePageController(user, user), "Reddit - Profile Page " + user.getUserName());
    }

    private void login(){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../LoginPage.fxml",new LoginPageController(), "Reddit - Login Page");
    }

    private void homePage(){
        loginButton.getScene().getWindow().hide();

        OpenWindow.openWindow("../HomePage.fxml",new HomePageController(null), "Reddit - HomePage");
    }

}