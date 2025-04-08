package org.software.testing;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MovieRecommendationServiceTest {

    @Test
    public void generateRecommendationsWithValidUsersAndMovies() throws IOException {
        List<User> users = Arrays.asList(
                new User("Alice", "123", Arrays.asList("M1")),
                new User("Bob", "456", Arrays.asList("M2"))
        );
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "M1", Arrays.asList("Sci-Fi")),
                new Movie("The Matrix", "M2", Arrays.asList("Action"))
        );

        MovieRecommendationService movieService = new MovieRecommendationService(new GenreBasedRecommendation());

        Map<User, List<Movie>> recommendationList = movieService.generateRecommendations(users, movies);

        // Basic assertions to check if recommendations are generated
        assertEquals(2, recommendationList.size());
        for (User user : users) {
            assertTrue(recommendationList.containsKey(user));
            List<Movie> recommended = recommendationList.get(user);
            assertTrue(recommended.size() > 0);
        }
    }

    @Test
    public void generateRecommendationsWithEmptyUsers() throws IOException {
        List<User> users = Collections.emptyList();
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "M1", Arrays.asList("Sci-Fi")),
                new Movie("The Matrix", "M2", Arrays.asList("Action"))
        );

        MovieRecommendationService movieService = new MovieRecommendationService(new GenreBasedRecommendation());

        Map<User, List<Movie>> recommendationList = movieService.generateRecommendations(users, movies);

        // Assert that no recommendations are generated for an empty user list
        assertEquals(0, recommendationList.size());
    }

    @Test
    public void generateRecommendationsWithEmptyMovies() throws IOException {
        List<User> users = Arrays.asList(
                new User("Alice", "123", Arrays.asList("M1")),
                new User("Bob", "456", Arrays.asList("M2"))
        );
        List<Movie> movies = Collections.emptyList();

        MovieRecommendationService movieService = new MovieRecommendationService(new GenreBasedRecommendation());

        Map<User, List<Movie>> recommendationList = movieService.generateRecommendations(users, movies);

        // Assert that users have empty recommendations when no movies are provided
        assertEquals(2, recommendationList.size());
        for (User user : users) {
            assertTrue(recommendationList.containsKey(user));
            List<Movie> recommended = recommendationList.get(user);
            assertTrue(recommended.isEmpty());
        }
    }

    @Test
    public void generateRecommendationsWithNullMovies() throws IOException {
        List<User> users = Arrays.asList(
                new User("Alice", "123", Arrays.asList("M1")),
                new User("Bob", "456", Arrays.asList("M2"))
        );

        MovieRecommendationService movieService = new MovieRecommendationService(new GenreBasedRecommendation());

        Map<User, List<Movie>> recommendationList = movieService.generateRecommendations(users, null);

        // Assert that users have empty recommendations when null movies are provided
        assertEquals(2, recommendationList.size());
        for (User user : users) {
            assertTrue(recommendationList.containsKey(user));
            List<Movie> recommended = recommendationList.get(user);
            assertTrue(recommended.isEmpty());
        }
    }

    @Test
    public void generateRecommendationsWithNullUsers() throws IOException {
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "M1", Arrays.asList("Sci-Fi")),
                new Movie("The Matrix", "M2", Arrays.asList("Action"))
        );

        MovieRecommendationService movieService = new MovieRecommendationService(new GenreBasedRecommendation());

        Map<User, List<Movie>> recommendationList = movieService.generateRecommendations(null, movies);

        // Assert that no recommendations are generated for null user list
        assertEquals(0, recommendationList.size());
    }
}
