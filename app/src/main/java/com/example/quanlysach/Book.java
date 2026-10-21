package com.example.quanlysach;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String status;
    private float rating;
    private String note;

    public Book(String title, String author, String genre,
                String status, float rating, String note) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        this.rating = rating;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }

    public float getRating() {
        return rating;
    }

    public String getNote() {
        return note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

