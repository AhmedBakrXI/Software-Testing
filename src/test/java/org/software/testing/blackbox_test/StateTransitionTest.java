package org.software.testing.blackbox_test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.software.testing.*;

import java.util.List;
import java.util.Map;

public class StateTransitionTest {
    private ValidationService validationService;
    private MovieRecommendationService recommendationService;

    @Test
    public void testStateSequences() {
        // Reset system by reinitializing services
        resetSystem();
        try {
            recommendationService.generateRecommendations(List.of(), List.of());
            fail("Expected IllegalStateException for empty recommendations");
        } catch (IllegalStateException e) {
            // Exception is expected, test passes
        }

        // Load users before movies
        resetSystem();
        List<User> users = loadUsers("test_files/users2.txt");
        List<Movie> movies = loadMovies("test_files/movies1.txt");
        validationService = new ValidationService(movies, users);
        try {
            recommendationService.generateRecommendations(users, movies);
            fail("Expected IllegalStateException for loading users before movies");
        } catch (IllegalStateException e) {
            // Exception is expected, test passes
        }

        // Correct sequence
        resetSystem();
        List<Movie> loadedMovies = loadMovies("test_files/movies1.txt");
        List<User> loadedUsers = loadUsers("test_files/users1.txt");
        validationService = new ValidationService(loadedMovies, loadedUsers);
        validationService.validate();
        Map<User, List<Movie>> recs = recommendationService.generateRecommendations(loadedUsers, loadedMovies);
        assertNotNull("Recommendations should not be null", recs);
        assertFalse("Recommendations should not be empty", recs.isEmpty());
    }

    private void resetSystem() {
        validationService = null;
        recommendationService = new MovieRecommendationService(new GenreBasedRecommendation());
    }

    private List<User> loadUsers(String filePath) {
        try {
            return FileReader.readUsers(filePath);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load users from " + filePath, e);
        }
    }

    private List<Movie> loadMovies(String filePath) {
        try {
            return FileReader.readMovies(filePath);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load movies from " + filePath, e);
        }
    }
}