package com.view.controller;

import com.exception.VoteException;
import com.jfoenix.controls.*;
import com.model.Comment;
import com.model.Post;
import com.model.User;
import com.model.VoteType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class CommentCellController extends JFXListCell<Comment> {

    private User user;
    private Post post;

    public CommentCellController(User user, Post post){
        this.user = user;
        this.post = post;
    }

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Circle userImage;

    @FXML
    private Label username;

    @FXML
    private Label date;

    @FXML
    private JFXButton replyButton;

    @FXML
    private JFXTextArea commentText;

    @FXML
    private JFXListView<Comment> replyCommentList;

    @FXML
    private Label upVote;

    @FXML
    private Label upVoteNumber;

    @FXML
    private Label downVote;

    @FXML
    private Label downVoteNumber;

    @FXML
    private Label error;

    private FXMLLoader fxmlLoader;
    private ObservableList<Comment> replyComments;

    @FXML
    public void initialize(){

    }

    public void updateItem(Comment comment, boolean empty) {
        super.updateItem(comment, empty);

        if (empty || comment == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("../CommentCell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Image img = new Image(comment.getUser().getPathImage());
            if(!img.isError()) {
                userImage.setFill(new ImagePattern(img));
            }else{
                comment.getUser().setPathImage("com/assert/reddit.png");
                Image img2 = new Image(post.getSubReddit().getImagePath());
                userImage.setFill(new ImagePattern(img2));
            }

            error.setText("");
            username.setText(comment.getUser().getUserName());
            date.setText(comment.getDate().toString());
            commentText.setText(comment.getText());
            upVoteNumber.setText(String.valueOf(comment.upVote()));
            downVoteNumber.setText(String.valueOf(comment.downVote()));

            replyComments = FXCollections.observableArrayList();
            replyComments.addAll(comment.getComments());
            replyCommentList.setItems(replyComments);
            replyCommentList.setCellFactory(CommentCellController -> new CommentCellController(user, post));


            replyButton.setOnAction(event -> reply(comment));

            upVote.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> vote(comment, VoteType.UpVote));
            downVote.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> vote(comment, VoteType.DownVote));

            setText(null);
            setGraphic(rootAnchorPane);

        }
    }
    private void reply(Comment comment){
        error.setText("");
        if(user == null){
            error.setText("Please first login or signup");
        }else {
            replyButton.getScene().getWindow();
            OpenWindow.openWindowWait("../WriteComment.fxml", new WriteCommentController(user, comment), "Reddit - Write Comment");
            replyComments.add(0, comment.getComments().get(0));
            replyCommentList.refresh();
        }
    }

    private void vote(Comment comment, VoteType voteType){
        error.setText("");
        if(user == null){
            error.setText("Please first login or signup");
        }else{
            try {
                user.voteComment(comment, voteType);
                upVoteNumber.setText(String.valueOf(comment.upVote()));
                downVoteNumber.setText(String.valueOf(comment.downVote()));
            } catch (VoteException e) {
                error.setText(e.getMessage());
            }
        }
    }

}
