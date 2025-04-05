package org.software.testing;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public record Movie(String title, String id, List<String> genres) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title)
                && Objects.equals(id, movie.id)
                && Objects.equals(new HashSet<>(genres), new HashSet<>(movie.genres));
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
