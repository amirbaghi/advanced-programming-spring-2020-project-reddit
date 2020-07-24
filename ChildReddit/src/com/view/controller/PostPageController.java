package com.view.controller;

import com.exception.BeingMember;
import com.exception.NotExistSubRedditException;
import com.exception.VoteException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.model.*;
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


public class PostPageController {

    private User user;
    private Post post;
    public PostPageController(Post post, User user){
        this.post = post;
        this.user = user;
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
    private JFXButton loginButton1;

    @FXML
    private JFXButton signupButton1;

    @FXML
    private JFXTextArea titleArea;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXButton attachFile;

    @FXML
    private JFXListView<Comment> commentList;

    @FXML
    private JFXButton writeCommentButton;

    @FXML
    private ImageView writeCommentIcon;

    @FXML
    private Label username;

    @FXML
    private Label datePost;

    @FXML
    private Circle subRedditImage;

    @FXML
    private Label subRedditName;

    @FXML
    private JFXTextArea subRedditAbout;

    @FXML
    private JFXButton joinButton;

    @FXML
    private Label loginSignupLabel;

    @FXML
    private Label upVote;

    @FXML
    private Label upVoteNumber;

    @FXML
    private Label downVote;

    @FXML
    private Label downVoteNumber;

    @FXML
    private Label commentNumber;

    @FXML
    private Label voteError;

    @FXML
    private Label numberMembersLabel;

    @FXML
    private Label numberPostLabel;

    @FXML
    private Label joinError;

    @FXML
    private ImageView profile;

    private ObservableList<Comment> comments;

    @FXML
    public void initialize() {

        joinError.setText("");

        numberMembersLabel.setText(post.getSubReddit().members());
        numberPostLabel.setText(post.getSubReddit().numberOfPost());

        comments = FXCollections.observableArrayList();
        comments.addAll(post.getComments());
        commentList.setItems(comments);
        commentList.setCellFactory(CommentCellController -> new CommentCellController(user, post));

        attachFile.setVisible(true);
        if(post.getAttach() == null){
            attachFile.setVisible(false);
        }
        if(post.getSubReddit().getUsers().contains(user)){
            joinButton.setVisible(false);
        }

        if(user==null){
            logoutButton.setVisible(false);
            profile.setVisible(false);
            writeCommentButton.setVisible(false);
            writeCommentIcon.setVisible(false);
        }else{
            loginButton.setVisible(false);
            signupButton.setVisible(false);
            loginButton1.setVisible(false);
            signupButton1.setVisible(false);
            loginSignupLabel.setVisible(false);
        }

        voteError.setText("");
        username.setText(post.getUser().getUserName());

        subRedditName.setText(post.getSubReddit().getName());
        subRedditAbout.setText(post.getSubReddit().getDescription());
        Image img = new Image(post.getSubReddit().getImagePath());
        if(!img.isError()) {
            subRedditImage.setFill(new ImagePattern(img));
        }else{
            post.getSubReddit().setImagePath("com/assert/reddit-icon.png");
            Image img2 = new Image(post.getSubReddit().getImagePath());
            subRedditImage.setFill(new ImagePattern(img2));
        }

        titleArea.setText(post.getTitle());
        textArea.setText(post.getText());

        datePost.setText(post.getDate().toString());

        upVoteNumber.setText(String.valueOf(post.upVote()));
        downVoteNumber.setText(String.valueOf(post.downVote()));

        commentNumber.setText(String.valueOf(post.commentsNumber()));

        redditIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());
        redditLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());
        logoutButton.setOnAction(event -> logout());

        attachFile.setOnAction(event -> showAttachFile());

        upVote.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> vote(VoteType.UpVote));
        downVote.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> vote(VoteType.DownVote));

        subRedditName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> subRedditPage());
        subRedditImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> subRedditPage());

        loginButton.setOnAction(event -> login());
        loginButton1.setOnAction(event -> login());

        signupButton.setOnAction(event -> signup());
        signupButton1.setOnAction(event -> signup());

        joinButton.setOnAction(event -> join());

        username.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> userPage());

        profile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage());

        searchIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> search());

        writeCommentIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> writeComment());
        writeCommentButton.setOnAction(event -> writeComment());

    }

    private void search(){
        String s = searchBox.getText().trim();
        searchIcon.getScene().getWindow().hide();
        OpenWindow.openWindow("../ResultSearchPage.fxml", new ResultSearchPageController(user,s),
        "Reddit - Search '" + s + "'");
    }

    private void homePage(){
        redditIcon.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(user), "Reddit - HomePage");
    }

    private void logout(){
        logoutButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(null), "Reddit - HomePage");
    }


    private void showAttachFile(){
        attachFile.getScene().getWindow();
        AttachType attachType = post.getAttach().getAttachType();
        String path = post.getAttach().getPath();
        if(attachType.equals(AttachType.IMAGE)){
            OpenWindow.openWindowWait("../ShowImage.fxml", new ShowImageController(path), "Reddit - Attach File Show Image");
        }
        else {
            String newPath = path.replace("\\", "/");
            newPath = newPath.replace("file:","file:///");
            OpenWindow.openWindowWait("../MediaPlayer.fxml", new MediaPlayerController(newPath), "Reddit - Attach File Media View");
        }
    }

    private void vote(VoteType voteType){
        voteError.setText("");
        if(this.user == null){
            voteError.setText("Please first Login or Signup");
        }else{
            try {
                this.user.votePost(post, voteType);
                upVoteNumber.setText(String.valueOf(post.upVote()));
                downVoteNumber.setText(String.valueOf(post.downVote()));

            } catch (VoteException e) {
                voteError.setText(e.getMessage());
            }
        }
    }

    private void subRedditPage(){
        subRedditName.getScene().getWindow().hide();
        OpenWindow.openWindow("../SubRedditPage.fxml", new SubRedditPageController(user, post.getSubReddit())
        ,"Reddit - SubReddit " + post.getSubReddit().getName());
    }

    private void login(){
        loginButton.getScene().getWindow();
        ShortLoginController shortLoginController = new ShortLoginController(user);
        OpenWindow.openWindowWait("../ShortLogin.fxml", shortLoginController, "Reddit - Login");
        user = shortLoginController.getUser();

        if(post.getSubReddit().getUsers().contains(user)){
            commentList.setCellFactory(CommentCellController -> new CommentCellController(user, post));
            joinButton.setVisible(false);
            voteError.setText("");
            joinError.setText("");
        }

        if(user!=null){
            profile.setVisible(true);
            logoutButton.setVisible(true);
            writeCommentButton.setVisible(true);
            writeCommentIcon.setVisible(true);
            loginButton.setVisible(false);
            signupButton.setVisible(false);
            loginButton1.setVisible(false);
            signupButton1.setVisible(false);
            loginSignupLabel.setVisible(false);
            joinError.setText("");
            voteError.setText("");
        }
    }

    private void join(){
        joinError.setText("");
        if(user == null){
            joinError.setText("Please first login or signup");
        }else{
            try {
                user.memberSubReddit(post.getSubReddit());
                numberMembersLabel.setText(post.getSubReddit().members());
                joinButton.setVisible(false);
            } catch (BeingMember beingMember) {
                beingMember.printStackTrace();
            } catch (NotExistSubRedditException e) {
                e.printStackTrace();
            }
        }
    }

    private void signup(){
        signupButton.getScene().getWindow().hide();
        OpenWindow.openWindow("../SignupPage.fxml", new SignupPageController(), "Reddit - Signup Page");
    }

    private void userPage(){
        username.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(post.getUser(), user),
                "Reddit - Profile Page " + post.getUser().getUserName());
    }

    private void profilePage(){
        profile.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(user,user),
                "Reddit - Profile Page " + user.getUserName());
    }

    private void writeComment(){
        writeCommentButton.getScene().getWindow();
        OpenWindow.openWindowWait("../WriteComment.fxml", new WriteCommentController(user, post), "Reddit - Write comment");
        comments.add(0, post.getComments().get(0));
        commentList.refresh();
    }

}
