package org.software.testing.blackbox_test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.software.testing.Movie;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class BoundaryValueAnalysisTest {
    private final String movieId;
    private final boolean valid;

    private final List<Movie> movies = Arrays.asList(
        new Movie("Movie123", "123", Arrays.asList("Action")),
        new Movie("Movie456", "456", Arrays.asList("Drama"))
    );

    public BoundaryValueAnalysisTest(String movieId, boolean valid) {
        this.movieId = movieId;
        this.valid = valid;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"12", false},   // 2 digits (below boundary)
            {"123", true},   // 3 digits (valid)
            {"1234", false}  // 4 digits (above boundary)
        });
    }

    @Test
    public void testMovieIdLength() {
        if (valid) {
            Movie movie = findMovieById(movieId);
            assertNotNull(movie);
            assertEquals(movieId, movie.id());
        } else {
            try {
                findMovieById(movieId);
                fail("Expected IllegalArgumentException for invalid movie ID: " + movieId);
            } catch (IllegalArgumentException e) {
                assertEquals("Invalid movie ID: " + movieId, e.getMessage());
            }
        }
    }

    private Movie findMovieById(String movieId) {
        return movies.stream()
            .filter(movie -> movie.id().equals(movieId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
    }
}