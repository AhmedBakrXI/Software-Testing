package org.software.testing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MovieRecommendationServiceTest {
    private static List<User> users;
    private static List<Movie> movies;
    private static MovieRecommendationService movieService;
    static Map<User, List<Movie>> recommendationList;

    @Test
    public void generateRecommendationsWithValidUsersAndMovies() throws IOException {
         users = Arrays.asList(
                new User("Alice", "123", Arrays.asList("M1")),
                new User("Bob", "456", Arrays.asList("M2"))
        );
         movies = Arrays.asList(
                new Movie("Inception", "M1", Arrays.asList("Sci-Fi")),
                new Movie("The Matrix", "M2", Arrays.asList("Action")),
                new Movie("Blade Runner 2049", "M3", Arrays.asList("Sci-Fi")),
                new Movie("Mad Max: Fury Road", "M4", Arrays.asList("Action")),
                new Movie("Edge of Tomorrow", "M5", Arrays.asList("Sci-Fi")),
                new Movie("Star Trek", "M6", Arrays.asList("Sci-Fi")),
                new Movie("The Dark Knight", "M7", Arrays.asList("Action"))
        );

        recommendationList = movieService.generateRecommendations(users, movies);

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
         users = Collections.emptyList();
         movies = Arrays.asList(
                new Movie("Inception", "M1", Arrays.asList("Sci-Fi")),
                new Movie("The Matrix", "M2", Arrays.asList("Action"))
        );



        recommendationList = movieService.generateRecommendations(users, movies);

        // Assert that no recommendations are generated for an empty user list
        assertEquals(0, recommendationList.size());
    }

    @Test
    public void generateRecommendationsWithEmptyMovies() throws IOException {
         users = Arrays.asList(
                new User("Alice", "123", Arrays.asList("M1")),
                new User("Bob", "456", Arrays.asList("M2"))
        );
         movies = Collections.emptyList();

        

        recommendationList = movieService.generateRecommendations(users, movies);

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
         users = Arrays.asList(
                new User("Alice", "123", Arrays.asList("M1")),
                new User("Bob", "456", Arrays.asList("M2"))
        );

       
         recommendationList = movieService.generateRecommendations(users, null);

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
        movies = Arrays.asList(
                new Movie("Inception", "M1", Arrays.asList("Sci-Fi")),
                new Movie("The Matrix", "M2", Arrays.asList("Action"))
        );

       

        recommendationList = movieService.generateRecommendations(null, movies);

        // Assert that no recommendations are generated for null user list
        assertEquals(0, recommendationList.size());
    }



    @BeforeClass
    public static void setUpAll() {
        users = null;
        movies = null;
        movieService = new MovieRecommendationService(new GenreBasedRecommendation());
    }

    @Before
    public void setUpEach() {
        users = null;
        movies = null;
    }
    
    @AfterClass
    public static void tearDownAll() {
        users = null;
        movies = null;
        movieService = null;
        recommendationList = null;
        System.gc(); // Suggest garbage collection
    }

    @After
    public void tearDownEach() {
        users = null;
        movies = null;
        recommendationList = null;
    }

}
