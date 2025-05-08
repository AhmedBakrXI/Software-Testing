package org.software.testing;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void testRecommendWithMatchingGenre() {
        User user = new User("Alice", "12345X", Arrays.asList("TN222")); // Likes "The Notebook"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Expected: John Wick and Inception (same genres, not the same ID)
        assertEquals(1, recommendations.size());
        assertTrue(recommendations.contains( new Movie("Black Bag", "BB214", Arrays.asList("Drama"))));
    }

    @Test
    public void testRecommendCheckNoDuplicateRecommendations() {
        User user = new User("Alice", "12345X", Arrays.asList("TM123")); // Likes "The Matrix"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Expected: John Wick and Inception (same genres, not the same ID)
        assertEquals(2, recommendations.size());
        assertTrue(recommendations.contains(new Movie("John Wick", "JW456", Arrays.asList("Action", "Thriller"))));
        assertTrue(recommendations.contains(new Movie("Inception", "I111", Arrays.asList("Action", "Sci-Fi"))));
    }
    @Test
    public void testRecommendWhenUserHasManyFavorites() {
        User user = new User("Alice", "12345X", Arrays.asList("JW456","BB214")); // Likes "John Wick" and "Black Bag"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // Expected: John Wick and Inception (same genres, not the same ID)
        assertEquals(3, recommendations.size());
        assertTrue(recommendations.contains(new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"))));
        assertTrue(recommendations.contains(new Movie("Inception", "I111", Arrays.asList("Action", "Sci-Fi"))));
        assertTrue(recommendations.contains( new Movie("The Notebook", "TN222", Arrays.asList("Romance", "Drama"))));
    }

    @Test
    public void testRecommendWithNoMatchingGenres() {
        User user = new User("Bob", "98765Y", Arrays.asList("FN789")); // Likes "Finding Nemo"
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        // No other movies with Animation or Family
        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWhenUserHasNoFavorites() {
        User user = new User("Charlie", "45678Z", Collections.<String>emptyList());
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWithEmptyMovieList() {
        User user = new User("Daisy", "23456A", Arrays.asList("TM123"));
        List<Movie> recommendations = recommender.recommend(user, Collections.<Movie>emptyList());

        assertTrue(recommendations.isEmpty());
    }

    @Test
    public void testRecommendWhenFavoriteMovieIdNotFound() {
        User user = new User("Eli", "54321B", Arrays.asList("NON_EXISTENT"));
        List<Movie> recommendations = recommender.recommend(user, allMovies);

        assertTrue(recommendations.isEmpty());
    }




}