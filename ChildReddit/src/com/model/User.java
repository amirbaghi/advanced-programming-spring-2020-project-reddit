package com.model;

import com.exception.BeingMember;
import com.exception.NotExistSubRedditException;
import com.exception.UserExistException;
import com.exception.VoteException;
import com.view.WriteData;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userName;
    private String password;
    private Gender gender;
    private String pathImage;
    private String info;
    String age;
    String email;
    private ArrayList<SubReddit> userSubReddits;

    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    private User(String userName, String password, Gender gender) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        userSubReddits = new ArrayList<>();
        pathImage = "com/assert/reddit.png";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public String getAge() { return age; }

    public void setAge(String age) { this.age = age; }

    public String getPathImage() { return pathImage; }

    public void setPathImage(String pathImage) { this.pathImage = pathImage; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<SubReddit> getSubReddits() {
        return userSubReddits;
    }

    public void setSubReddits(ArrayList<SubReddit> subReddits) {
        this.userSubReddits = subReddits;
    }

    public static User searchUser(String username){
        for(User user: users){
            if(user.getUserName().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> search(String s){
        String s1 = s.toLowerCase();
        ArrayList<User> usersFind = new ArrayList<>();
        for(User user: users){
            if(user.getUserName().toLowerCase().contains(s1)){
                usersFind.add(user);
            }
        }
        return usersFind;
    }

    public static User login(String userName, String password){
        User user = User.searchUser(userName);
        if(user == null){
            return null;
        }
        else{
            if(user.getPassword().equals(password)){
                return user;
            }
            return null;
        }
    }

    public static User signUp(String username, String password, Gender gender)
            throws UserExistException {

        User user = User.searchUser(username);
        if(user != null){
            throw new UserExistException("This username before used!");
        }

        user = new User(username, password, gender);
        users.add(user);
        WriteData.writeUsers();
        return user;
    }

    public boolean changePassword(String oldPassword, String newPassword){
        if(oldPassword.equals(password)){
            password = newPassword;
            return true;
        }
        return false;
    }

    public SubReddit createSubReddit(String subRedditName){
        SubReddit subReddit = new SubReddit(this,subRedditName);
        userSubReddits.add(0, subReddit);
        SubReddit.addSubReddit(subReddit);
        return subReddit;
    }

    public boolean leaveSubReddit(SubReddit subReddit){
        for(SubReddit subReddit1: userSubReddits){
            if(subReddit.equals(subReddit1)){
                userSubReddits.remove(subReddit1);
                subReddit1.removeUser(this);
                return true;
            }
        }
        return false;
    }

    public void memberSubReddit(SubReddit subReddit) throws BeingMember, NotExistSubRedditException {
        if(userSubReddits.contains(subReddit)){
            throw new BeingMember("You are currently a member of the SubReddit");
        }
        for(SubReddit subReddit1: SubReddit.getSubRedditCreated()){
            if(subReddit1.equals(subReddit)){
                userSubReddits.add(subReddit);
                subReddit.addUser(this);
                return;
            }
        }
        throw new NotExistSubRedditException("This subreddit not exist");
    }

    public Post addPost(SubReddit subReddit, String title, String text) throws NotExistSubRedditException {
        for(SubReddit subReddit1: userSubReddits){
            if(subReddit.equals(subReddit1)){
                Post post = new Post(this, subReddit, title, text);
                subReddit.addPost(post);
                return post;
            }
        }
        throw new NotExistSubRedditException("This subreddit not exist");
    }

    public void deletePost(SubReddit subReddit, Post post){
        subReddit.removePost(post);
    }

    public int votePost(Post post, VoteType voteType) throws VoteException {
        int result = post.addVote(this, voteType);
        return result;
    }

    public int numberPostInSubReddit(SubReddit subReddit){
        int n = 0;
        for(Post post: subReddit.getPosts()){
            if(post.getUser().equals(this)){
                ++n;
            }
        }
        return n;
    }

    public int averageUpVotePost(SubReddit subReddit){
        int n = 0;
        int i=0;
        for(Post post: subReddit.getPosts()){
            if(post.getUser().equals(this)){
                n+=post.upVote();
                ++i;
            }
        }
        if(i==0){
            return 0;
        }
        return (n/i)*100;
    }

    public int averageDownVotePost(SubReddit subReddit){
        int n = 0;
        int i=1;
        for(Post post: subReddit.getPosts()){
            if(post.getUser().equals(this)){
                n+=post.downVote();
                ++i;
            }
        }
        if(i==0){
            return 0;
        }
        return (n/i)*100;
    }

    public void addCommentToPost(String text, Post post){
        Comment comment = new Comment(this, text);
        post.addComment(comment);
    }

    public void replyComment(String text, Comment comment){
        Comment comment1 = new Comment(this, text);
        comment.addComment(comment1);
    }

    public int voteComment(Comment comment, VoteType voteType) throws VoteException {
        int result = comment.addVote(this, voteType);
        return result;
    }

}
