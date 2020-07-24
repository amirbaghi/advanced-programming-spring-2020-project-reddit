package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.model.Post;
import com.model.SubReddit;
import com.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ResultSearchPageController {
    private User user;
    String s;
    public ResultSearchPageController(User user, String s){
        this.user = user;
        this.s = s;
    }

    @FXML
    private TextField searchBox;

    @FXML
    private ImageView searchIcon;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXListView<Post> postList;

    @FXML
    private JFXListView<User> userList;

    @FXML
    private JFXListView<SubReddit> subRedditList;

    @FXML
    private ImageView profile;

    private ObservableList<Post> posts;
    private ObservableList<User> users;
    private ObservableList<SubReddit> subReddits;

    @FXML
    public void initialize() {

        if(user == null){
            logoutButton.setVisible(false);
            profile.setVisible(false);
        }else{
            loginButton.setVisible(false);
            signupButton.setVisible(false);
        }

        posts = FXCollections.observableArrayList();
        posts.addAll(Post.search(s));
        postList.setItems(posts);
        postList.setCellFactory(PostCellHomePageController  -> new PostCellHomePageController(user));

        users = FXCollections.observableArrayList();
        users.addAll(User.search(s));
        userList.setItems(users);
        userList.setCellFactory(UserCellResultSearchController -> new UserCellResultSearchController(user));

        subReddits = FXCollections.observableArrayList();
        subReddits.addAll(SubReddit.search(s));
        subRedditList.setItems(subReddits);
        subRedditList.setCellFactory(SubRedditCellResultSearchController -> new SubRedditCellResultSearchController(user));

        searchIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());

        loginButton.setOnAction(event -> login());
        signupButton.setOnAction(event -> signup());
        logoutButton.setOnAction(event -> logout());
        profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage());
    }

    private void search(){
        String s = searchBox.getText().trim();
        searchIcon.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(user, s),
                "Reddit - Search '" + s + "'");
    }

    private void login(){
        loginButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../LoginPage.fxml", new LoginPageController(), "Reddit - Login Page");
    }

    private void signup(){
        signupButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../SignupPage.fxml", new SignupPageController(), "Reddit - Signup Page");
    }

    private void logout(){
        logoutButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(null), "Reddit - Home Page");
    }

    private void profilePage(){
        profile.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(user, user),
                "Reddit - Profile Page " + user.getUserName());
    }
}
