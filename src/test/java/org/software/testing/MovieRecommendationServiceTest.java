package org.software.testing;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MovieRecommendationServiceTest {
    @Test
    public void generateRecommendations() throws IOException {
        String path1 = "test_files/users1.txt";
        String path2= "test_files/movies1.txt";

        

        List<User> users = FileReader.readUsers(path1);
        List<Movie> movies = FileReader.readMovies(path2);

        
        MovieRecommendationService movieService = new MovieRecommendationService(new GenreBasedRecommendation());

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
