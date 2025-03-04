package org.software.testing;

import java.util.List;
import java.util.Objects;

public class Movie {
    private final String title;
    private final String id;
    private final List<String> genres;

    public Movie(String title, String id, List<String> genres) {
        this.title = title;
        this.id = id;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title)
                && Objects.equals(id, movie.id)
                && Objects.equals(genres, movie.genres);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", genres=" + genres +
                '}';
    }
}
