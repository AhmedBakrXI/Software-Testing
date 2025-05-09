package org.software.testing;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FileReaderWhiteBoxTest {

    @Test
    public void testReadUsersValidInput() throws IOException {
        // Arrange
        String path = "test_files/validUsers.txt";
        createTestFile(path, "Ahmed Hassan,12345678A\nTSR001,TG002\nSara Mohamed,23456789B\nTDK003,I004\n");

        // Act
        List<User> users = FileReader.readUsers(path);

        // Assert
        assertEquals(2, users.size());
        assertEquals("Ahmed Hassan", users.get(0).name());
        assertEquals("12345678A", users.get(0).id());
        assertEquals(List.of("TSR001", "TG002"), users.get(0).favouriteMovieIds());
        assertEquals("Sara Mohamed", users.get(1).name());
        assertEquals("23456789B", users.get(1).id());
        assertEquals(List.of("TDK003", "I004"), users.get(1).favouriteMovieIds());
    }

    @Test
    public void testReadUsersEmptyFile() throws IOException {
        // Arrange
        String path = "test_files/emptyUsers.txt";
        createTestFile(path, "");

        // Act
        List<User> users = FileReader.readUsers(path);

        // Assert
        assertTrue(users.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReadUsersInvalidFormat() throws IOException {
        // Arrange
        String path = "test_files/invalidUsers.txt";
        createTestFile(path, "Ahmed Hassan,12345678A\nTSR001,TG002\nSara Mohamed");

        // Act
        FileReader.readUsers(path);
    }

    @Test
    public void testReadMoviesValidInput() throws IOException {
        // Arrange
        String path = "test_files/validMovies.txt";
        createTestFile(path, "The Shawshank Redemption,TSR001\nDrama\nThe Godfather,TG002\nCrime,Drama\n");

        // Act
        List<Movie> movies = FileReader.readMovies(path);

        // Assert
        assertEquals(2, movies.size());
        assertEquals("The Shawshank Redemption", movies.get(0).title());
        assertEquals("TSR001", movies.get(0).id());
        assertEquals(List.of("Drama"), movies.get(0).genres());
        assertEquals("The Godfather", movies.get(1).title());
        assertEquals("TG002", movies.get(1).id());
        assertEquals(List.of("Crime", "Drama"), movies.get(1).genres());
    }

    @Test
    public void testReadMoviesEmptyFile() throws IOException {
        // Arrange
        String path = "test_files/emptyMovies.txt";
        createTestFile(path, "");

        // Act
        List<Movie> movies = FileReader.readMovies(path);

        // Assert
        assertTrue(movies.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReadMoviesInvalidFormat() throws IOException {
        // Arrange
        String path = "test_files/invalidMovies.txt";
        createTestFile(path, "The Shawshank Redemption,TSR001\nDrama\nThe Godfather");

        // Act
        FileReader.readMovies(path);
    }

    private void createTestFile(String path, String content) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}