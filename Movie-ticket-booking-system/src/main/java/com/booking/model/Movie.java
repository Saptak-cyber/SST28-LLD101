package com.booking.model;

// Single Responsibility: Represents movie information only
public class Movie {
    private final String id;
    private final String title;
    private final int duration; // in minutes
    private final String genre;
    private final String rating;

    public Movie(String id, String title, int duration, String genre, String rating) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }
}
