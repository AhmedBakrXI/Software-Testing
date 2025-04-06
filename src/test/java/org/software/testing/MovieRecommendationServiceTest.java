package org.software.testing;

import org.junit.Test;

public class MovieRecommendationServiceTest {
    @Test
    public void generateRecommendations() {
        List<User> users = Arrays.asList(new User("Alice"), new User("Bob"));
        List<Movie> movies = Arrays.asList(new Movie("Inception"), new Movie("The Matrix"));

        MovieRecommendationService movieService = new MovieRecommendationService(new RecommendationStrategy());

        Map<User, List<Movie>> recommendationList = movieService.generateRecommendations(users, movies);

        // Basic assertions to check if all users got all movies recommended
        assertEquals(2, recommendationList.size());
        for (User user : users) {
            assertTrue(recommendationList.containsKey(user));
            List<Movie> recommended = recommendationList.get(user);
            assertEquals(2, recommended.size());
            assertTrue(recommended.containsAll(movies));
        }
    }
}
