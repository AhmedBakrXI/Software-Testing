package org.software.testing.integration_test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.software.testing.GenreBasedRecommendation;
import org.software.testing.Movie;
import org.software.testing.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GenreBasedRecommendationWithMocksTest {

    private GenreBasedRecommendation recommender;

    @Mock
    private User mockUser;

    @Mock
    private List<Movie> mockMovies;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        recommender = new GenreBasedRecommendation();
    }

    @Test
    public void testRecommendWithMatchingGenre() {
        // Mock user and movie data
        when(mockUser.favouriteMovieIds()).thenReturn(Collections.singletonList("TM123"));

        Movie movie1 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        Movie movie2 = new Movie("John Wick", "JW456", Arrays.asList("Action", "Thriller"));
        Movie movie3 = new Movie("Inception", "I111", Arrays.asList("Action", "Sci-Fi"));

        when(mockMovies.iterator()).thenReturn(Arrays.asList(movie1, movie2, movie3).iterator());

        // Call the method under test
        List<Movie> recommendations = recommender.recommend(mockUser, mockMovies);

        // Verify the results
        assertEquals(2, recommendations.size());
        assertTrue(recommendations.contains(movie2));
        assertTrue(recommendations.contains(movie3));
    }

    @Test
    public void testRecommendWithNoMatchingGenres() {
        // Mock user and movie data
        when(mockUser.favouriteMovieIds()).thenReturn(Collections.singletonList("FN789"));

        Movie movie1 = new Movie("Finding Nemo", "FN789", Arrays.asList("Animation", "Family"));
        Movie movie2 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));

        when(mockMovies.iterator()).thenReturn(Arrays.asList(movie1, movie2).iterator());

        // Call the method under test
        List<Movie> recommendations = recommender.recommend(mockUser, mockMovies);

        // Verify the results
        assertTrue(recommendations.isEmpty());
    }
}