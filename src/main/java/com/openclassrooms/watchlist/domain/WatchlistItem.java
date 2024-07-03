package com.openclassrooms.watchlist.domain;

import com.openclassrooms.watchlist.validation.BadMovie;
import com.openclassrooms.watchlist.validation.GoodMovie;
import com.openclassrooms.watchlist.validation.ValidPriority;
import com.openclassrooms.watchlist.validation.ValidRating;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@GoodMovie
@BadMovie
public class WatchlistItem {

    @NotEmpty(message = "Please enter the title")
    private String title;

    @ValidRating
    private double rating;

    @Size(max = 50, message = "Comment should be maximum 50 characters")
    private String comment;

    private static int index;

    private int id;

    @ValidPriority
    private Priority priority;

    public WatchlistItem(String title, double rating, String comment, Priority priority) {
        this.title = title;
        this.rating = rating;
        this.comment = comment;
        this.priority = priority;
        this.id = index++;
    }

    public WatchlistItem() {
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WatchlistItem that = (WatchlistItem) o;
        return Double.compare(that.rating, rating) == 0 &&
                id == that.id && title.equals(that.title) && rating == that.rating && comment.equals(that.comment)
                && priority == that.priority;
    }

}
