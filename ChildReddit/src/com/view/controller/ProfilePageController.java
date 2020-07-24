package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import java.io.File;

import com.model.SubReddit;
import com.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;


public class ProfilePageController {
    private User owner;
    private User user;

    public ProfilePageController(User owner, User user){
        this.owner = owner;
        this.user = user;
    }

    @FXML
    private Circle imagePlace;

    @FXML
    private Label redditLabel;

    @FXML
    private ImageView redditIcon;

    @FXML
    private ImageView searchIcon;

    @FXML
    private TextField searchBox;

    @FXML
    private Label username;

    @FXML
    private Label age;

    @FXML
    private Label infoLabel;

    @FXML
    private ImageView infoIcon;

    @FXML
    private ImageView passwordIcon;

    @FXML
    private Label passwordLabel;

    @FXML
    private JFXListView<SubReddit> subRedditList;

    @FXML
    private JFXButton changeImage;

    @FXML
    private Label genderLabel;

    @FXML
    private TextArea infoBox;

    @FXML
    private TextArea email;

    @FXML
    private ImageView createSubRedditImage1;

    @FXML
    private ImageView createSubRedditImage2;

    @FXML
    private Label createSubReddit;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXButton editInfoButton;

    @FXML
    private ImageView editInfoIcon;

    @FXML
    private JFXButton editEmailButton;

    @FXML
    private ImageView editEmailIcon;

    @FXML
    private JFXButton editAgeButton;

    @FXML
    private ImageView editAgeIcon;

    @FXML
    private ImageView profile;

    private ObservableList<SubReddit> subReddits;

    @FXML
    public void initialize() {

        Image img = new Image(owner.getPathImage());
        if(!img.isError()) {
             imagePlace.setFill(new ImagePattern(img));
        }else{
            user.setPathImage("com/assert/reddit.png");
            Image img2 = new Image(owner.getPathImage());
            imagePlace.setFill(new ImagePattern(img2));
        }

        if(!owner.equals(user)){
            editInfoButton.setVisible(false);
            editInfoIcon.setVisible(false);
            editAgeButton.setVisible(false);
            editAgeIcon.setVisible(false);
            editEmailIcon.setVisible(false);
            editEmailButton.setVisible(false);
            passwordLabel.setVisible(false);
            passwordIcon.setVisible(false);
            createSubReddit.setVisible(false);
            createSubRedditImage1.setVisible(false);
            createSubRedditImage2.setVisible(false);
            changeImage.setVisible(false);
        }else{
            profile.setVisible(false);
        }

        if(user == null){
            logoutButton.setVisible(false);
            profile.setVisible(false);
        }else{
            loginButton.setVisible(false);
            signupButton.setVisible(false);
        }

        username.setText(owner.getUserName());
        genderLabel.setText(owner.getGender().toString());
        email.setText(owner.getEmail());
        age.setText(owner.getAge());
        infoBox.setText(owner.getInfo());

        subReddits = FXCollections.observableArrayList();
        subReddits.addAll(owner.getSubReddits());
        subRedditList.setItems(subReddits);
        subRedditList.setCellFactory(CellSubRedditController -> new SubRedditCellProfilePageController(owner, user));

        imagePlace.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> showImage());
        changeImage.setOnAction(event -> {imageChange();});

        editInfoIcon.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {info();});
        editInfoButton.setOnAction(event -> {info();});

        editEmailIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {emailEdit();});
        editEmailButton.setOnAction(event -> {emailEdit();});

        editAgeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {ageEdit();});
        editAgeButton.setOnAction(event -> {ageEdit();});

        passwordIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {changePassword();});
        passwordLabel.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {changePassword();});

        createSubReddit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {subRedditCreate();});
        createSubRedditImage1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {subRedditCreate();});
        createSubRedditImage2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {subRedditCreate();});

        logoutButton.setOnAction(event -> logout());

        redditIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());
        redditLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());

        loginButton.setOnAction(event -> login());
        signupButton.setOnAction(event -> signup());
        profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage());

        searchIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());
    }

    private void search(){
        String s = searchBox.getText().trim();
        searchIcon.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(user, s),
                "Reddit - Search '" + s + "s");
    }

    private void profilePage(){
        profile.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(user, user),
                "Reddit - Profile Page " + user.getUserName());
    }

    private void signup(){
        signupButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../SignupPage.fxml", new SignupPageController(), "Reddit - Signup Page");
    }

    private void login(){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../LoginPage.fxml", new LoginPageController(), "Reddit - Login Page");
    }

    private void logout(){
        imagePlace.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(null),"Reddit - HomePage");
    }

    private void showImage(){
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../ShowImage.fxml", new ShowImageController(owner.getPathImage()), "Reddit - Image ");
    }

    private void imageChange(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File","*.png","*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            owner.setPathImage("file:"+selectedFile.getAbsolutePath());
            imagePlace.setFill(new ImagePattern(new Image(owner.getPathImage())));
        }
    }

    private void info(){
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../SetInfo.fxml", new SetInfoController(owner),"Reddit - Set Info");
        infoBox.setText(owner.getInfo());
    }

    private void emailEdit(){
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../EditEmail.fxml", new EditEmailController(owner),"Reddit - Edit Email");
        email.setText(owner.getEmail());
    }

    private void ageEdit(){
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../EditAge.fxml", new EditAgeController(owner),"Reddit - Edit Age");
        age.setText(owner.getAge());
    }

    private void changePassword(){
        OpenWindow.openWindowWait("../ChangePassword.fxml", new ChangePasswordController(owner),"Reddit - Change Password");
    }

    private void subRedditCreate(){
        int len = owner.getSubReddits().size() + 1;
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../CreateSubReddit.fxml", new CreateSubRedditController(owner),"Reddit - Create SubReddit");
        if(len == owner.getSubReddits().size()) {
            subReddits.add(0,owner.getSubReddits().get(0));
            subRedditList.setItems(subReddits);
        }
    }

    private void homePage(){
        redditLabel.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml",new HomePageController(user), "Reddit - HomePage");
    }
}