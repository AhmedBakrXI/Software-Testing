package org.software.testing.white_box;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import org.software.testing.*;

import static org.junit.Assert.*;

public class MovieTest {

    @Test
    public void testEquals_SameObject() {
        Movie movie = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        assertTrue(movie.equals(movie)); // Same object should be equal
    }

    @Test
    public void testEquals_NullObject() {
        Movie movie = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        assertFalse(movie.equals(null)); // Should not be equal to null
    }

    @Test
    public void testEquals_DifferentClass() {
        Movie movie = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        String other = "Not a Movie";
        assertFalse(movie.equals(other)); // Should not be equal to a different class
    }

    @Test
    public void testEquals_DifferentTitle() {
        Movie movie1 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        Movie movie2 = new Movie("Inception", "TM123", Arrays.asList("Action", "Sci-Fi"));
        assertFalse(movie1.equals(movie2)); // Different titles should not be equal
    }

    @Test
    public void testEquals_DifferentId() {
        Movie movie1 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        Movie movie2 = new Movie("The Matrix", "TM456", Arrays.asList("Action", "Sci-Fi"));
        assertFalse(movie1.equals(movie2)); // Different IDs should not be equal
    }

    @Test
    public void testEquals_DifferentGenres() {
        Movie movie1 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        Movie movie2 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Thriller"));
        assertFalse(movie1.equals(movie2)); // Different genres should not be equal
    }

    @Test
    public void testEquals_SameValues() {
        Movie movie1 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        Movie movie2 = new Movie("The Matrix", "TM123", Arrays.asList("Sci-Fi", "Action"));
        assertTrue(movie1.equals(movie2)); // Same values (order of genres doesn't matter) should be equal
    }

    @Test
    public void testToString() {
        Movie movie = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        String expected = "Movie{title='The Matrix', id='TM123', genres=[Action, Sci-Fi]}";
        assertEquals(expected, movie.toString()); // Verify the string representation
    }
}