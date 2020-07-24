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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class PostCellHomePageController extends JFXListCell<Post> {
    private User user;
    public PostCellHomePageController(User user){this.user = user;}

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Circle imagePlaceUser;

    @FXML
    private Label username;

    @FXML
    private Label date;

    @FXML
    private JFXTextArea titleArea;

    @FXML
    private Label commentNumberLabel;

    @FXML
    private Label upVoteLabel;

    @FXML
    private Label downVoteLabel;

    @FXML
    private JFXButton joinButton;

    @FXML
    private Label joinError;

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
                fxmlLoader = new FXMLLoader(getClass().getResource("../PostCellHomePage.fxml"));
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
            joinError.setText("");
            titleArea.setText(post.getTitle());

            username.setText(user.getUserName());
            date.setText(post.getDate().toString());
            commentNumberLabel.setText(post.commentsNumber());
            upVoteLabel.setText(String.valueOf(post.upVote()));
            downVoteLabel.setText(String.valueOf(post.downVote()));
            if(post.getSubReddit().getUsers().contains(this.user)){
                joinButton.setVisible(false);
            }


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


    private void join(Post post){
        joinError.setText("");
        if(user == null){
            joinError.setText("Please first login or signup");
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
