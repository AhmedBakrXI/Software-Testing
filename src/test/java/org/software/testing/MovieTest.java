package org.software.testing;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MovieTest {
    @Test
    public void equals_sameAttributes_returnsTrue() {
        Movie movie1 = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        Movie movie2 = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        assertEquals(movie1, movie2);
    }

    @Test
    public void equals_differentTitle_returnsFalse() {
        Movie movie1 = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        Movie movie2 = new Movie("Interstellar", "001", List.of("Sci-Fi", "Thriller"));
        assertNotEquals(movie1, movie2);
    }

    @Test
    public void equals_differentId_returnsFalse() {
        Movie movie1 = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        Movie movie2 = new Movie("Inception", "002", List.of("Sci-Fi", "Thriller"));
        assertNotEquals(movie1, movie2);
    }

    @Test
    public void equals_differentGenres_returnsFalse() {
        Movie movie1 = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        Movie movie2 = new Movie("Inception", "001", List.of("Action", "Adventure"));
        assertNotEquals(movie1, movie2);
    }

    @Test
    public void equals_sameGenresDifferentOrder_returnsTrue() {
        Movie movie1 = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        Movie movie2 = new Movie("Inception", "001", List.of("Thriller", "Sci-Fi"));
        assertEquals(movie1, movie2);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Movie movie = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        assertNotEquals(null, movie);
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Movie movie = new Movie("Inception", "001", List.of("Sci-Fi", "Thriller"));
        String expected = "Movie{title='Inception', id='001', genres=[Sci-Fi, Thriller]}";
        assertEquals(expected, movie.toString());
    }
}