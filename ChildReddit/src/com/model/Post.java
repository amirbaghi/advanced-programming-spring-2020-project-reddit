package com.model;

import com.exception.VoteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {
    private User user;
    private SubReddit subReddit;
    private String title;
    private String text;
    private Date date;
    private ArrayList<Vote> votes;
    private ArrayList<Comment> comments;
    private Attach attach;

    public Post(User user,SubReddit subReddit,String title, String text) {
        this.user = user;
        this.subReddit = subReddit;
        this.title = title;
        this.text = text;
        date = new Date();
        votes = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public User getUser(){ return user; }

    public SubReddit getSubReddit(){ return subReddit; }

    public Post(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() { return text; }

    public void setText(String text) {
        this.text = text;
    }

    public Attach getAttach() { return attach; }

    public void setAttach(Attach attach) { this.attach = attach; }

    public Date getDate() { return date; }

    public ArrayList<Vote> getVotes() {  return votes; }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int addVote(User user, VoteType voteType) throws VoteException {
        for(Vote vote: votes){
            if(vote.getUser().equals(user)){
                if(vote.getVoteType().equals(voteType)){
                    throw new VoteException("You before " + voteType.toString() + " this post");
                }else{
                    vote.setVoteType(voteType);
                    return 0;
                }
            }
        }
        votes.add(new Vote(user, voteType));
        return 1;
    }

    public void addComment(Comment comment){
        comments.add(0, comment);
    }

    public String commentsNumber(){
        int n=0;
        for(Comment comment: comments){
            n += comment.getComments().size();
        }
        n += comments.size();
        String comment = toByte(n);
        return comment;
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

    private String toByte(int x){
        String y = String.valueOf(x);
        if(x>=100 && x<=1024){
            y = String.format("%.2f",(double)x / 1024);
        }
        else if(x>1024){
            y = String.format("%.2f",(double)x/(1024*1024));
        }
        return y;
    }

    public static ArrayList<Post> search(String s){
        String s1 = s.toLowerCase();
        ArrayList<Post> postFind = new ArrayList<>();
        for(Post post: SubReddit.allPostInSubReddit()){
            if(post.getTitle().toLowerCase().contains(s1) || post.getText().toLowerCase().contains(s1)){
                postFind.add(post);
            }
        }
        return postFind;
    }
}
