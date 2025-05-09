package org.software.testing.white_box;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.software.testing.*;
import static org.junit.Assert.*;

public class GenreBasedRecommendationTest {

    private GenreBasedRecommendation recommender;
    private List<Movie> allMovies;

    @Before
    public void setUp() {
        recommender = new GenreBasedRecommendation();

        allMovies = Arrays.asList(
                new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi")),
                new Movie("John Wick", "JW456", Arrays.asList("Action", "Thriller")),
                new Movie("Finding Nemo", "FN789", Arrays.asList("Animation", "Family")),
                new Movie("Inception", "I111", Arrays.asList("Action", "Sci-Fi")),
                new Movie("The Notebook", "TN222", Arrays.asList("Romance", "Drama")),
                new Movie("Black Bag", "BB214", Arrays.asList("Drama"))
        );
    }

    @Test
    public void testRecommendWithMatchingGenres() {
        User user = new User("Alice", "12345X", Arrays.asList("TN222")); // Likes "The Notebook"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Expected: Black Bag (same genre "Drama", not the same ID)
        assertEquals(1, recommendations.size());
        assertTrue(recommendations.contains(new Movie("Black Bag", "BB214", Arrays.asList("Drama"))));
    }

    @Test
    public void testRecommendWithNoMatchingGenres() {
        User user = new User("Bob", "98765Y", Arrays.asList("FN789")); // Likes "Finding Nemo"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // No other movies with "Animation" or "Family"
        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWhenUserHasNoFavorites() {
        User user = new User("Charlie", "45678Z", Collections.emptyList());
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // No favorite movies, so no recommendations
        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWithEmptyMovieList() {
        User user = new User("Daisy", "23456A", Arrays.asList("TM123"));
        List<Movie> recommendations = recommender.recommend(user, Collections.emptyList());

        // No movies available to recommend
        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWhenFavoriteMovieIdNotFound() {
        User user = new User("Eli", "54321B", Arrays.asList("NON_EXISTENT"));
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Favorite movie ID not found in the list
        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWithMultipleFavoriteMovies() {
        User user = new User("Frank", "67890C", Arrays.asList("TM123", "TN222")); // Likes "The Matrix" and "The Notebook"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Expected: John Wick, Inception, and Black Bag
        assertEquals(3, recommendations.size());
        assertTrue(recommendations.contains(new Movie("John Wick", "JW456", Arrays.asList("Action", "Thriller"))));
        assertTrue(recommendations.contains(new Movie("Inception", "I111", Arrays.asList("Action", "Sci-Fi"))));
        assertTrue(recommendations.contains(new Movie("Black Bag", "BB214", Arrays.asList("Drama"))));
    }

    @Test
    public void testRecommendAvoidDuplicateRecommendations() {
        User user = new User("Grace", "11223D", Arrays.asList("TM123")); // Likes "The Matrix"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Expected: John Wick and Inception (no duplicates)
        assertEquals(2, recommendations.size());
        assertTrue(recommendations.contains(new Movie("John Wick", "JW456", Arrays.asList("Action", "Thriller"))));
        assertTrue(recommendations.contains(new Movie("Inception", "I111", Arrays.asList("Action", "Sci-Fi"))));
    }
}