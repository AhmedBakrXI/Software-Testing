package org.software.testing;

import java.util.List;
import java.util.Objects;

public class User {
    private final String name;
    private final String id;
    private final List<String> favouriteMovieIds;

    public User(String name, String id, List<String> favouriteMovieIds) {
        this.name = name;
        this.id = id;
        this.favouriteMovieIds = favouriteMovieIds;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<String> getFavouriteMovieIds() {
        return favouriteMovieIds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(id, user.id)
                && Objects.equals(favouriteMovieIds, user.favouriteMovieIds);
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
