package org.software.testing;

import java.util.List;

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
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", favouriteMovieIds=" + favouriteMovieIds +
                '}';
    }
}
