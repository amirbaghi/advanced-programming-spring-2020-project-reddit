package com.view.controller;

import com.exception.BeingMember;
import com.exception.NotExistSubRedditException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.model.Post;
import com.model.SubReddit;
import com.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;


import java.io.File;


public class SubRedditPageController {

    private User user;
    private SubReddit subReddit;

    public SubRedditPageController(User user, SubReddit subReddit) {
        this.user = user;
        this.subReddit = subReddit;
    }

    @FXML
    private Label redditLabel;

    @FXML
    private ImageView redditIcon;

    @FXML
    private ImageView searchIcon;

    @FXML
    private TextField searchBox;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXButton joinButton;

    @FXML
    private JFXButton leaveButton;

    @FXML
    private Circle imagePlace;

    @FXML
    private JFXButton changeImage;

    @FXML
    private JFXTextArea aboutArea;

    @FXML
    private JFXButton editAboutButton;

    @FXML
    private ImageView editAboutIcon;

    @FXML
    private Label subRedditNameLabel;

    @FXML
    private JFXListView<User> membersList;

    @FXML
    private JFXListView<Post> postsList;

    @FXML
    private ImageView newPostIcon;

    @FXML
    private JFXButton newPostButton;

    @FXML
    private Label numberMembersLabel;

    @FXML
    private Label numberPostLabel;

    @FXML
    private Label createDate;

    @FXML
    private Label joinError;

    @FXML
    private ImageView profile;

    private ObservableList<User> users;
    private ObservableList<Post> posts;

    @FXML
    public void initialize() {

        joinError.setText("");

        if(subReddit.getUsers().contains(user)){
            joinButton.setVisible(false);
        }else{
            leaveButton.setVisible(false);
            newPostButton.setVisible(false);
            newPostIcon.setVisible(false);
            changeImage.setVisible(false);
            editAboutButton.setVisible(false);
            editAboutIcon.setVisible(false);
        }

        if(user == null){
            logoutButton.setVisible(false);
            profile.setVisible(false);
        }else{
            loginButton.setVisible(false);
            signupButton.setVisible(false);
        }

        Image img = new Image(subReddit.getImagePath());
        if(!img.isError()) {
            imagePlace.setFill(new ImagePattern(img));
        }else{
            subReddit.setImagePath("com/assert/reddit.png");
            Image img2 = new Image(subReddit.getImagePath());
            imagePlace.setFill(new ImagePattern(img2));
        }

        numberMembersLabel.setText(subReddit.members());
        numberPostLabel.setText(subReddit.numberOfPost());
        subRedditNameLabel.setText(subReddit.getName());
        aboutArea.setText(subReddit.getDescription());
        createDate.setText(subReddit.getDate().toString());

        users = FXCollections.observableArrayList();
        users.addAll(subReddit.getUsers());
        membersList.setItems(users);
        membersList.setCellFactory(UserCellController -> new UserCellController(user, subReddit));

        posts = FXCollections.observableArrayList();
        posts.addAll(subReddit.getPosts());
        postsList.setItems(posts);
        postsList.setCellFactory(PostCellSubRedditController -> new PostCellSubRedditController(user));

        logoutButton.setOnAction(event -> {logout();});

        imagePlace.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {showImage();});
        changeImage.setOnAction(event -> {imageChange();});

        editAboutIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {about();});
        editAboutButton.setOnAction(event -> {about();});

        newPostIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {newPost();});
        newPostButton.setOnAction(event -> {newPost();});

        loginButton.setOnAction(event -> login());
        signupButton.setOnAction(event -> signup());

        joinButton.setOnAction(event -> join());

        leaveButton.setOnAction(event -> leave());

        redditIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());
        redditLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());

        profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage());

        searchIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());
    }

    private void search(){
        String s = searchBox.getText().trim();
        searchIcon.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(user, s),
                "ChildReddit - Search '" + s + "'");
    }

    private void profilePage(){
        profile.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(user,user),
                "ChildReddit - Profile Page " + user.getUserName());
    }

    private void homePage(){
        redditLabel.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(user), "ChildReddit - HomePage");
    }

    private void logout(){
        logoutButton.getScene().getWindow().hide();

        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(null), "ChildReddit - HomePge");
    }

    private void showImage(){
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../ShowImage.fxml", new ShowImageController(subReddit.getImagePath()),"ChildReddit - Image " + subReddit.getName());
    }

    private void imageChange(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File","*.png","*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            subReddit.setImagePath("file:"+selectedFile.getAbsolutePath());
            imagePlace.setFill(new ImagePattern(new Image(subReddit.getImagePath())));
        }
    }

    private void about(){
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../SetInfo.fxml",new SetInfoController(subReddit),"ChildReddit - Set info");
        aboutArea.setText(subReddit.getDescription());
    }

    private void newPost(){
        int len = subReddit.getPosts().size() + 1;
        imagePlace.getScene().getWindow();
        OpenWindow.openWindowWait("../NewPost.fxml", new NewPostController(user, subReddit),
                "New Post to " + subReddit.getName() + "from " + user.getUserName());
        if(len == subReddit.getPosts().size()) {
            posts.add(0,subReddit.getPosts().get(0));
            postsList.setItems(posts);
            numberPostLabel.setText(subReddit.numberOfPost());
        }

    }

    private void login(){
        loginButton.getScene().getWindow();
        ShortLoginController shortLoginController = new ShortLoginController(user);
        OpenWindow.openWindowWait("../ShortLogin.fxml", shortLoginController, "ChildReddit - Login");
        user = shortLoginController.getUser();
        if(subReddit.getUsers().contains(user)){
            membersList.setCellFactory(UserCellController -> new UserCellController(user, subReddit));
            postsList.setCellFactory(PostCellSubRedditController -> new PostCellSubRedditController(user));
            joinButton.setVisible(false);
            leaveButton.setVisible(true);
            changeImage.setVisible(true);
            newPostIcon.setVisible(true);
            newPostButton.setVisible(true);
            editAboutIcon.setVisible(true);
            editAboutButton.setVisible(true);
        }

        if(user!=null){
            logoutButton.setVisible(true);
            profile.setVisible(true);
            loginButton.setVisible(false);
            signupButton.setVisible(false);
        }
    }

    private void signup(){
        signupButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../SignupPage.fxml", new SignupPageController(), "ChildReddit - Signup Page");
    }

    private void join(){
        joinError.setText("");
        if(user == null){
            joinError.setText("Please first login or signup");
        }else{
            try {
                user.memberSubReddit(subReddit);
                numberMembersLabel.setText(subReddit.members());
                joinButton.setVisible(false);
                leaveButton.setVisible(true);
                changeImage.setVisible(true);
                newPostIcon.setVisible(true);
                newPostButton.setVisible(true);
                editAboutIcon.setVisible(true);
                editAboutButton.setVisible(true);
                users.add(subReddit.getUsers().get(subReddit.getUsers().size()-1));
                membersList.refresh();
                postsList.refresh();
            } catch (BeingMember beingMember) {
                beingMember.printStackTrace();
            } catch (NotExistSubRedditException e) {
                joinError.setText(e.getMessage());
            }
        }
    }

    private void leave(){
        user.leaveSubReddit(subReddit);
        users.remove(user);
        membersList.refresh();
        joinButton.setVisible(true);
        leaveButton.setVisible(false);
        changeImage.setVisible(false);
        newPostIcon.setVisible(false);
        newPostButton.setVisible(false);
        editAboutIcon.setVisible(false);
        editAboutButton.setVisible(false);
    }

}
