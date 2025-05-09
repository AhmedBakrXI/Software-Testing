package org.software.testing.white_box;

    import org.junit.Before;
    import org.junit.Test;

    import java.util.Arrays;
    import java.util.Collections;
    import java.util.List;
    import java.util.Map;

    import org.software.testing.*;
    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertTrue;

    public class MovieRecommendationServiceTest {

        private MovieRecommendationService movieService;
        private RecommendationStrategy mockStrategy;

        @Before
        public void setUp() {
            mockStrategy = new GenreBasedRecommendation();
            movieService = new MovieRecommendationService(mockStrategy);
        }

        @Test
        public void testGenerateRecommendations_WithValidUsersAndMovies() {
            List<User> users = Arrays.asList(
                    new User("Alice", "123", Arrays.asList("I001")), // Matches "Inception"
                    new User("Bob", "456", Arrays.asList("TM002"))  // Matches "The Matrix"
            );
            List<Movie> movies = Arrays.asList(
                    new Movie("Inception", "I001", Arrays.asList("Sci-Fi")),
                    new Movie("The Matrix", "TM002", Arrays.asList("Action")),
                    new Movie("Blade Runner 2049", "BR003", Arrays.asList("Sci-Fi"))
            );

            Map<User, List<Movie>> recommendations = movieService.generateRecommendations(users, movies);

            assertEquals(2, recommendations.size());
            for (User user : users) {
                assertTrue(recommendations.containsKey(user));
            }
        }

        @Test
        public void testGenerateRecommendations_WithEmptyUsers() {
            List<User> users = Collections.emptyList();
            List<Movie> movies = Arrays.asList(
                    new Movie("Inception", "I001", Arrays.asList("Sci-Fi")),
                    new Movie("The Matrix", "TM002", Arrays.asList("Action"))
            );

            Map<User, List<Movie>> recommendations = movieService.generateRecommendations(users, movies);

            assertEquals(0, recommendations.size());
        }

        @Test
        public void testGenerateRecommendations_WithEmptyMovies() {
            List<User> users = Arrays.asList(
                    new User("Alice", "123", Arrays.asList("I001")),
                    new User("Bob", "456", Arrays.asList("TM002"))
            );
            List<Movie> movies = Collections.emptyList();

            Map<User, List<Movie>> recommendations = movieService.generateRecommendations(users, movies);

            assertEquals(2, recommendations.size());
            for (User user : users) {
                assertTrue(recommendations.containsKey(user));
                assertTrue(recommendations.get(user).isEmpty());
            }
        }
    }