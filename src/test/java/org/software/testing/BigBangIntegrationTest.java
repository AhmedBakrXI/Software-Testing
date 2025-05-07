package org.software.testing;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BigBangIntegrationTest {

    private ValidationService validationService;
    private MovieRecommendationService movieRecommendationService;
    private List<User> users;
    private List<Movie> movies;

    @Before
    public void setUp() {
        // Initialize test data
        users = Arrays.asList(
                new User("Hassan Ali", "12345678X", Arrays.asList("TSR001", "TDK003")),
                new User("Ali Mohamed", "87654321W", Arrays.asList("TG002"))
        );

        movies = new ArrayList<>(Arrays.asList(
                new Movie("The Shawshank Redemption", "TSR001", Arrays.asList("Drama")),
                new Movie("The Godfather", "TG002", Arrays.asList("Crime", "Drama")),
                new Movie("The Dark Knight", "TDK003", Arrays.asList("Action", "Crime", "Drama"))
        ));


    }

    @Test
    public void testBigBangIntegration() {
        // Initialize services
        validationService = new ValidationService(movies, users);
        RecommendationStrategy recommendationStrategy = new GenreBasedRecommendation();
        movieRecommendationService = new MovieRecommendationService(recommendationStrategy);

        // Step 1: Validate data
        validationService.validate();

        // Step 2: Generate recommendations
        Map<User, List<Movie>> recommendations = movieRecommendationService.generateRecommendations(users, movies);

        // Step 3: Assert results
        assertEquals(2, recommendations.size());

    }

    @Test
    public void testBigBangIntegrationWithEmptyUsers() {
        // Initialize services
        validationService = new ValidationService(movies, Collections.emptyList());
        RecommendationStrategy recommendationStrategy = new GenreBasedRecommendation();
        movieRecommendationService = new MovieRecommendationService(recommendationStrategy);

        // Step 1: Validate data
        validationService.validate();

        // Step 2: Generate recommendations
        Map<User, List<Movie>> recommendations = movieRecommendationService.generateRecommendations(Collections.emptyList(), movies);

        // Step 3: Assert results
        assertEquals(0, recommendations.size());
    }

    @Test(expected = AppException.class)
    public void testBigBangIntegrationWithDuplicateMovies() {
        // Add duplicate movie
        movies.add(new Movie("The Shawshank Redemption", "TSR001", Arrays.asList("Drama")));

        // Initialize services
        validationService = new ValidationService(movies, users);
        RecommendationStrategy recommendationStrategy = new GenreBasedRecommendation();
        movieRecommendationService = new MovieRecommendationService(recommendationStrategy);

        // Step 1: Validate data
        validationService.validate();

        // Step 2: Generate recommendations
        Map<User, List<Movie>> recommendations = movieRecommendationService.generateRecommendations(users, movies);

        // Step 3: Assert results
        assertEquals(2, recommendations.size());
    }
    @Test(expected = AppException.class)
    public void testBigBangIntegrationWithInvalidUserData() {
        List<User> users = Arrays.asList(
                new User("Hassan Ali", "22", Arrays.asList("TSR001", "TDK003")),
                new User("Ali Mohamed", "87654321W", Arrays.asList("TG002"))
        );

        // Initialize services
        validationService = new ValidationService(movies, users);

        // Step 1: Validate data (should throw an exception)
        validationService.validate();
    }
    @Test(expected = AppException.class)
    public void testBigBangIntegrationWithEmptyMovieGenres() {
        movies.add(new Movie("Inception", "INC004", Collections.emptyList()));

        // Initialize services
        validationService = new ValidationService(movies, users);

        // Step 1: Validate data (should throw an exception)
        validationService.validate();
    }
    @Test(expected = AppException.class)
    public void testBigBangIntegrationWithInvalidMovieId() {
        movies.add(new Movie("Interstellar", "INT04", Arrays.asList("Sci-Fi")));

        // Initialize services
        validationService = new ValidationService(movies, users);

        // Step 1: Validate data (should throw an exception)
        validationService.validate();
    }
    @Test(expected = AppException.class)
    public void testBigBangIntegrationWithDuplicateUserIds() {
        List<User> users = Arrays.asList(
                new User("Hassan Ali", "12345678X", Arrays.asList("TSR001", "TDK003")),
                new User("Ali Mohamed", "12345678X", Arrays.asList("TG002"))
        );

        // Initialize services
        validationService = new ValidationService(movies, users);

        // Step 1: Validate data (should throw an exception)
        validationService.validate();
    }
}