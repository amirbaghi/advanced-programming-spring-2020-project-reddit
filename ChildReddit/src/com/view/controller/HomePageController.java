package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.model.Post;
import com.model.SubReddit;
import com.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class HomePageController {

    private User user;

    public HomePageController(User user){this.user = user;}

    @FXML
    private ImageView search;

    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private ImageView profile;

    @FXML
    private JFXListView<Post> postsList;

    @FXML
    private JFXComboBox<String> sort;

    @FXML
    private JFXButton showPostButton;

    @FXML
    private Label privacy;

    private ObservableList<Post> posts;

    @FXML
    public void initialize() {
        ObservableList<String> s = FXCollections.observableArrayList("New","Top");
        sort.setItems(s);
        sort.setValue("Top");

        if(user == null){
            logoutButton.setVisible(false);
            profile.setVisible(false);
        }else{
            signupButton.setVisible(false);
            loginButton.setVisible(false);
        }

        posts = FXCollections.observableArrayList();
        posts.addAll(SubReddit.sortVote());
        postsList.setItems(posts);
        postsList.setCellFactory(PostCellHomePageController -> new PostCellHomePageController(user));

        signupButton.setOnAction(event -> { signup(); });

        loginButton.setOnAction(event -> {login();});

        logoutButton.setOnAction(event -> logout());

        profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profile());

        search.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());

        showPostButton.setOnAction(event -> show());

        privacy.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> privacy());

    }

    private void show(){
        if(sort.getSelectionModel().getSelectedItem().equals("New")){
            posts.setAll(SubReddit.sortDate());
            postsList.refresh();
        }else{
            posts.setAll(SubReddit.sortVote());
            postsList.refresh();
        }
        postsList.setCellFactory(PostCellHomePageController -> new PostCellHomePageController(user));
    }

    private void signup(){
        signupButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../SignupPage.fxml",new SignupPageController(), "ChildReddit - Signup Page");

    }

    private void login(){
        loginButton.getScene().getWindow().hide();

        OpenWindow.openWindow("../LoginPage.fxml",new LoginPageController(), "ChildReddit - Login Page");
    }

    private void logout(){
        logoutButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml",new HomePageController(null), "ChildReddit - HomePage");
    }

    private void profile(){
        profile.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(user, user),
                "ChildReddit - Profile Page " + user.getUserName());
    }

    private void search(){
        String s = searchBox.getText().trim();
        searchBox.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(user, s),
                "ChildReddit - Search '" + s + "'");
    }

    private void privacy(){
        privacy.getScene().getWindow();
        OpenWindow.openWindowWait("../PrivacyAndPolicy.fxml", new PrivacyAndPolicyController(), "ChildReddit - Privacy And Policy");
    }
}
