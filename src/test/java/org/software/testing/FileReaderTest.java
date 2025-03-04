package org.software.testing;

import org.junit.Test;

import java.util.List;

public class FileReaderTest {
    @Test
    public void testReadUsersValidInput() {
        String path = "test_files/users1.txt";
        try {
            List<User> users = FileReader.readUsers(path);
            assert users.size() == 2;
            assert users.getFirst().getName().equals("Ahmed Hassan");
            assert users.getFirst().getId().equals("12345678A");
            assert users.getFirst().getFavouriteMovieIds().size() == 2;
            assert users.get(0).getFavouriteMovieIds().get(0).equals("TSR001");
            assert users.get(0).getFavouriteMovieIds().get(1).equals("TG002");
            assert users.get(1).getName().equals("Sara Mohamed");
            assert users.get(1).getId().equals("23456789B");
            assert users.get(1).getFavouriteMovieIds().size() == 2;
            assert users.get(1).getFavouriteMovieIds().get(0).equals("TDK003");
            assert users.get(1).getFavouriteMovieIds().get(1).equals("I004");
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testReadUsersEmptyFile() {
        String path = "test_files/emptyUsers.txt";
        try {
            List<User> users = FileReader.readUsers(path);
            assert users.isEmpty();
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testReadUsersInvalidUserId() {
        String path = "test_files/users2.txt";
        try {
            List<User> users = FileReader.readUsers(path);
            assert users.size() == 2;
            assert users.getFirst().getName().equals("Mohamed Saeed");
            assert users.getFirst().getId().equals("9a765432X");
            assert users.getFirst().getFavouriteMovieIds().size() == 1;
            assert users.get(0).getFavouriteMovieIds().get(0).equals("A123");
            assert users.get(1).getName().equals("Huda Yasser");
            assert users.get(1).getId().equals("87654321Y");
            assert users.get(1).getFavouriteMovieIds().size() == 1;
            assert users.get(1).getFavouriteMovieIds().get(0).equals("TG002");
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testReadMoviesValidInput() {
        String path = "test_files/movies1.txt";
        try {
            List<Movie> movies = FileReader.readMovies(path);
            assert movies.size() == 4;
            assert movies.getFirst().getTitle().equals("The Shawshank Redemption");
            assert movies.getFirst().getId().equals("TSR001");
            assert movies.getFirst().getGenres().size() == 1;
            assert movies.get(0).getGenres().get(0).equals("Drama");

            assert movies.get(1).getTitle().equals("The Godfather");
            assert movies.get(1).getId().equals("TG002");
            assert movies.get(1).getGenres().size() == 2;
            assert movies.get(1).getGenres().get(0).equals("Crime");
            assert movies.get(1).getGenres().get(1).equals("Drama");

            assert movies.get(2).getTitle().equals("The Dark Knight");
            assert movies.get(2).getId().equals("TDK003");
            assert movies.get(2).getGenres().size() == 3;
            assert movies.get(2).getGenres().get(0).equals("Action");
            assert movies.get(2).getGenres().get(1).equals("Crime");
            assert movies.get(2).getGenres().get(2).equals("Drama");

            assert movies.get(3).getTitle().equals("Inception");
            assert movies.get(3).getId().equals("I004");
            assert movies.get(3).getGenres().size() == 3;
            assert movies.get(3).getGenres().get(0).equals("Sci-Fi");
            assert movies.get(3).getGenres().get(1).equals("Action");
            assert movies.get(3).getGenres().get(2).equals("Thriller");
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testReadMoviesEmptyFile() {
        String path = "test_files/emptyMovies.txt";
        try {
            List<Movie> movies = FileReader.readMovies(path);
            assert movies.isEmpty();
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testReadMoviesInvalidMovieId() {
        String path = "test_files/movies2.txt";
        try {
            List<Movie> movies = FileReader.readMovies(path);
            assert movies.size() == 2;
            assert movies.get(0).getTitle().equals("Avatar");
            assert movies.get(0).getId().equals("AV1234");
            assert movies.get(0).getGenres().size() == 2;
            assert movies.get(0).getGenres().get(0).equals("Sci-Fi");
            assert movies.get(0).getGenres().get(1).equals("Fantasy");

            assert movies.get(1).getTitle().equals("The Godfather");
            assert movies.get(1).getId().equals("T02");
            assert movies.get(1).getGenres().size() == 2;
            assert movies.get(1).getGenres().get(0).equals("Crime");
            assert movies.get(1).getGenres().get(1).equals("Drama");
        } catch (Exception e) {
            assert false;
        }
    }
}