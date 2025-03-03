package org.software.testing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static List<User> readUsers(String path) throws IOException {
        File file = new File(path);
        List<String> lines = Files.readAllLines(file.toPath());

        List<User> users = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 2) {
            String[] userLine = lines.get(i).split(",");
            String[] favouriteMovieIds = lines.get(i + 1).split(",");

            String userName = userLine[0].trim();
            String userId = userLine[1].trim();

            List<String> favouriteMovieIdsList = new ArrayList<>();
            for (String movieId : favouriteMovieIds) {
                favouriteMovieIdsList.add(movieId.trim());
            }

            User user = new User(userName, userId, favouriteMovieIdsList);
            users.add(user);
        }

        return users;
    }

    public static List<Movie> readMovies(String path) throws IOException {
        File file = new File(path);
        List<String> lines = Files.readAllLines(file.toPath());

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 2) {
            String[] movieLine = lines.get(i).split(",");
            String[] genres = lines.get(i + 1).split(",");

            String movieTitle = movieLine[0].trim();
            String movieId = movieLine[1].trim();

            List<String> genresList = new ArrayList<>();
            for (String genre : genres) {
                genresList.add(genre.trim());
            }
            Movie movie = new Movie(movieTitle, movieId, genresList);
            movies.add(movie);
        }

        return movies;
    }
}
