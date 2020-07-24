package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SubReddit implements Serializable {

    private ArrayList<User> users;
    private String name;
    private String description;
    private String imagePath;
    private Date date;
    private ArrayList<Post> posts;

    private static ArrayList<SubReddit> subRedditsCreated = new ArrayList<>();

    public SubReddit(User user,String name) {
        this.name = name;
        imagePath = "com/assert/reddit-icon.png";
        users = new ArrayList<>();
        users.add(user);
        posts = new ArrayList<>();
        date = new Date();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public ArrayList<User> getUsers() { return users;}

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() { return imagePath; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Post> getPosts() { return posts; }

    public Date getDate() { return date; }

    public static ArrayList<SubReddit> getSubRedditCreated(){
        return SubReddit.subRedditsCreated;
    }

    public static void setSubRedditsCreated(ArrayList<SubReddit> subRedditsCreated) {
        SubReddit.subRedditsCreated = subRedditsCreated;
    }

    public static void addSubReddit(SubReddit subReddit){
        subRedditsCreated.add(subReddit);
    }

    public static SubReddit searchSubReddit(String name){
        for(SubReddit subReddit: SubReddit.subRedditsCreated){
            if(subReddit.getName().equals(name)){
                return subReddit;
            }
        }
        return null;
    }

    public void addPost(Post post){
        posts.add(0,post);
    }

    public void removePost(Post post){
        posts.remove(post);
    }

    public String members(){
        String member = toByte(users.size());
        return member;
    }

    public String numberOfPost() {
        String post = toByte(posts.size());
        return post;
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

    public static ArrayList<SubReddit> search(String s){
        String s1 = s.toLowerCase();
        ArrayList<SubReddit> subRedditsFind = new ArrayList<>();
        for(SubReddit subReddit: subRedditsCreated){
            if(subReddit.getName().toLowerCase().contains(s1)){
                subRedditsFind.add(subReddit);
            }
        }
        return subRedditsFind;
    }

    public static ArrayList<Post> allPostInSubReddit(){
        ArrayList<Post> posts = new ArrayList<>();
        for(SubReddit subReddit: SubReddit.getSubRedditCreated()){
            for(Post post: subReddit.getPosts()){
                posts.add(post);
            }
        }
        return posts;
    }

    public static ArrayList<Post> sortDate(){
        ArrayList<Post> posts = allPostInSubReddit();
        Collections.sort(posts, (a,b)-> b.getDate().compareTo(a.getDate()));
        return posts;
    }

    public static ArrayList<Post> sortVote(){
        ArrayList<Post> posts = allPostInSubReddit();
        Collections.sort(posts, (a,b) -> b.upVote() - a.upVote());
        return posts;
    }
}
