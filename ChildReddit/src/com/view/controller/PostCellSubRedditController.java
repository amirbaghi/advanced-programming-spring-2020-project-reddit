package com.view.controller;

import com.exception.BeingMember;
import com.exception.NotExistSubRedditException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXTextArea;
import com.model.Post;
import com.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class PostCellSubRedditController extends JFXListCell<Post> {
    private User user;
    public PostCellSubRedditController(User user){this.user = user;}

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextArea titleArea;

    @FXML
    private Circle imagePlaceUser;

    @FXML
    private Label username;

    @FXML
    private Label date;

    @FXML
    private JFXButton joinButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private Label commentNumberLabel;

    @FXML
    private Label upVoteLabel;

    @FXML
    private Label downVoteLabel;

    private FXMLLoader fxmlLoader;

    @FXML
    public void initialize(){

    }

    public void updateItem(Post post, boolean empty){
        super.updateItem(post, empty);

        if(empty || post == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("../PostCellSubReddit.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            User user = post.getUser();
            Image img = new Image(user.getPathImage());
            if(!img.isError()) {
                imagePlaceUser.setFill(new ImagePattern(img));
            }else{
                user.setPathImage("com/assert/reddit.png");
                Image img2 = new Image(user.getPathImage());
                imagePlaceUser.setFill(new ImagePattern(img2));
            }

            titleArea.setText(post.getTitle());

            username.setText(user.getUserName());
            date.setText(post.getDate().toString());
            commentNumberLabel.setText(post.commentsNumber());
            upVoteLabel.setText(String.valueOf(post.upVote()));
            downVoteLabel.setText(String.valueOf(post.downVote()));
            if(this.user==null || !post.getSubReddit().getUsers().contains(this.user)){
                deleteButton.setVisible(false);
                deleteIcon.setVisible(false);
            }else{
                joinButton.setVisible(false);
            }

            deleteButton.setOnAction(event -> {deletePost(post);});
            deleteIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {deletePost(post);});

            joinButton.setOnAction(event -> join(post));

            titleArea.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {postPage(post);});

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }

    private void postPage(Post post){
        titleArea.getScene().getWindow().hide();
        OpenWindow.openWindow("../PostPage.fxml",new PostPageController(post, user),
                "Reddit - Post Page " + post.getTitle());
    }

    private void deletePost(Post post){
        deleteIcon.getScene().getWindow();
        OpenWindow.openWindowWait("../DeletePostWarning.fxml",new DeletePostWarningController(post, getListView()),
                "Reddit - Delete Warning");
    }

    private void join(Post post){
        if(user == null){
            joinButton.getScene().getWindow().hide();

            OpenWindow.openWindow("../SignupPage.fxml",new SignupPageController(), "Reddit - Signup Page");
        }
        else{
            try {
                user.memberSubReddit(post.getSubReddit());
                joinButton.getScene().getWindow().hide();
                OpenWindow.openWindowWait("../SubRedditPage.fxml",new SubRedditPageController(user, post.getSubReddit()),
                        "Reddit - SubReddit Page " + post.getSubReddit().getName());
            } catch (BeingMember beingMember) {
                beingMember.printStackTrace();
            } catch (NotExistSubRedditException e) {
                e.printStackTrace();
            }
        }
    }

}
