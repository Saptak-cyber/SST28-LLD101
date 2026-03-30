package com.booking.model;

import java.time.LocalDateTime;

// Single Responsibility: Represents a movie showtime
public class Showtime {
    private final String id;
    private final Movie movie;
    private final LocalDateTime startTime;
    private final String screen;

    public Showtime(String id, Movie movie, LocalDateTime startTime, String screen) {
        this.id = id;
        this.movie = movie;
        this.startTime = startTime;
        this.screen = screen;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getScreen() {
        return screen;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(movie.getDuration());
    }
}
