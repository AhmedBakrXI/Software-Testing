package org.software.testing;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public record User(String name, String id, List<String> favouriteMovieIds) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(id, user.id)
                && Objects.equals(new HashSet<>(favouriteMovieIds), new HashSet<>(user.favouriteMovieIds));
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", favouriteMovieIds=" + favouriteMovieIds +
                '}';
    }
}
