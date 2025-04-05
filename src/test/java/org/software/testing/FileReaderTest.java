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
            assert users.getFirst().name().equals("Ahmed Hassan");
            assert users.getFirst().id().equals("12345678A");
            assert users.getFirst().favouriteMovieIds().size() == 2;
            assert users.get(0).favouriteMovieIds().get(0).equals("TSR001");
            assert users.get(0).favouriteMovieIds().get(1).equals("TG002");
            assert users.get(1).name().equals("Sara Mohamed");
            assert users.get(1).id().equals("23456789B");
            assert users.get(1).favouriteMovieIds().size() == 2;
            assert users.get(1).favouriteMovieIds().get(0).equals("TDK003");
            assert users.get(1).favouriteMovieIds().get(1).equals("I004");
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
            assert users.getFirst().name().equals("Mohamed Saeed");
            assert users.getFirst().id().equals("9a765432X");
            assert users.getFirst().favouriteMovieIds().size() == 1;
            assert users.get(0).favouriteMovieIds().getFirst().equals("A123");
            assert users.get(1).name().equals("Huda Yasser");
            assert users.get(1).id().equals("87654321Y");
            assert users.get(1).favouriteMovieIds().size() == 1;
            assert users.get(1).favouriteMovieIds().getFirst().equals("TG002");
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
            assert movies.getFirst().title().equals("The Shawshank Redemption");
            assert movies.getFirst().id().equals("TSR001");
            assert movies.getFirst().genres().size() == 1;
            assert movies.get(0).genres().getFirst().equals("Drama");

            assert movies.get(1).title().equals("The Godfather");
            assert movies.get(1).id().equals("TG002");
            assert movies.get(1).genres().size() == 2;
            assert movies.get(1).genres().get(0).equals("Crime");
            assert movies.get(1).genres().get(1).equals("Drama");

            assert movies.get(2).title().equals("The Dark Knight");
            assert movies.get(2).id().equals("TDK003");
            assert movies.get(2).genres().size() == 3;
            assert movies.get(2).genres().get(0).equals("Action");
            assert movies.get(2).genres().get(1).equals("Crime");
            assert movies.get(2).genres().get(2).equals("Drama");

            assert movies.get(3).title().equals("Inception");
            assert movies.get(3).id().equals("I004");
            assert movies.get(3).genres().size() == 3;
            assert movies.get(3).genres().get(0).equals("Sci-Fi");
            assert movies.get(3).genres().get(1).equals("Action");
            assert movies.get(3).genres().get(2).equals("Thriller");
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
            assert movies.getFirst().title().equals("Avatar");
            assert movies.getFirst().id().equals("AV1234");
            assert movies.getFirst().genres().size() == 2;
            assert movies.get(0).genres().get(0).equals("Sci-Fi");
            assert movies.get(0).genres().get(1).equals("Fantasy");

            assert movies.get(1).title().equals("The Godfather");
            assert movies.get(1).id().equals("T02");
            assert movies.get(1).genres().size() == 2;
            assert movies.get(1).genres().get(0).equals("Crime");
            assert movies.get(1).genres().get(1).equals("Drama");
        } catch (Exception e) {
            assert false;
        }
    }
}