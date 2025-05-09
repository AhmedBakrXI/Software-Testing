package org.software.testing.integration_test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.software.testing.GenreBasedRecommendation;
import org.software.testing.Movie;
import org.software.testing.MovieRecommendationService;
import org.software.testing.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MovieRecommendationServiceWithMocksTest {

    private MovieRecommendationService recommendationService;

    @Mock
    private GenreBasedRecommendation mockGenreBasedRecommendation;

    @Mock
    private List<User> mockUsers;

    @Mock
    private List<Movie> mockMovies;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendationService = new MovieRecommendationService(mockGenreBasedRecommendation);
    }

    @Test
    public void testGenerateRecommendationsWithValidData() {
        // Mock user and movie data
        User user1 = new User("Eslam", "12345678A", Collections.singletonList("TM123"));
        User user2 = new User("Ahmed", "87654321B", Collections.singletonList("JW456"));
        Movie movie1 = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        Movie movie2 = new Movie("John Wick", "JW456", Arrays.asList("Action", "Thriller"));

        when(mockUsers.iterator()).thenReturn(Arrays.asList(user1, user2).iterator());
        when(mockMovies.iterator()).thenReturn(Arrays.asList(movie1, movie2).iterator());

        // Mock GenreBasedRecommendation behavior
        when(mockGenreBasedRecommendation.recommend(user1, mockMovies)).thenReturn(Collections.singletonList(movie2));
        when(mockGenreBasedRecommendation.recommend(user2, mockMovies)).thenReturn(Collections.singletonList(movie1));

        // Call the method under test
        Map<User, List<Movie>> recommendations = recommendationService.generateRecommendations(mockUsers, mockMovies);

        // Verify the results
        assertEquals(2, recommendations.size());
        assertEquals(Collections.singletonList(movie2), recommendations.get(user1));
        assertEquals(Collections.singletonList(movie1), recommendations.get(user2));

        // Verify interactions
        verify(mockGenreBasedRecommendation).recommend(user1, mockMovies);
        verify(mockGenreBasedRecommendation).recommend(user2, mockMovies);
    }

    @Test
    public void testGenerateRecommendationsWithNoUsers() {
        // Mock empty user list
        when(mockUsers.iterator()).thenReturn(Collections.emptyIterator());

        // Call the method under test
        Map<User, List<Movie>> recommendations = recommendationService.generateRecommendations(mockUsers, mockMovies);

        // Verify the results
        assertEquals(0, recommendations.size());

        // Verify no interactions with GenreBasedRecommendation
        verifyNoInteractions(mockGenreBasedRecommendation);
    }
}