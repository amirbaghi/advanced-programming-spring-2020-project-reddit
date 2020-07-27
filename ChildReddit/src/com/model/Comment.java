package com.model;

import com.exception.VoteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Comment implements Serializable {
    private User user;
    private String text;
    private Date date;
    private ArrayList<Vote> votes;
    private ArrayList<Comment> comments;

    public Comment(User user, String text) {
        this.user = user;
        this.text = text;
        date = new Date();
        votes = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Vote> getVote() {
        return votes;
    }

    public Date getDate() { return date; }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int addVote(User user, VoteType voteType) throws VoteException {
        for(Vote vote: votes){
            if(vote.getUser().equals(user)){
                if(vote.getVoteType().equals(voteType)){
                    throw new VoteException("You before " + voteType.toString() + " this comment");
                }else{
                    vote.setVoteType(voteType);
                    return 0;
                }
            }
        }
        votes.add(new Vote(user, voteType));
        return 1;
    }

    public int upVote(){
        int n = 0;
        for(Vote vote: votes){
            if(vote.getVoteType().equals(VoteType.UpVote)){
                ++n;
            }
        }
        return n;
    }

    public int downVote(){
        int n = 0;
        for(Vote vote: votes){
            if(vote.getVoteType().equals(VoteType.DownVote)){
                ++n;
            }
        }
        return n;
    }

    public void addComment(Comment comment){
        comments.add(0,comment);
    }
}
