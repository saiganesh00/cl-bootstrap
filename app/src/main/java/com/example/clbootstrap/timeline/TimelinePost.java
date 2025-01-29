package com.example.clbootstrap.timeline;

public class TimelinePost {
    private String title;
    private String subtitle;
    private String time;
    private int likes;
    private int comments;

    public TimelinePost(String title, String subtitle, String time, int likes, int comments) {
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTime() {
        return time;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }
}
