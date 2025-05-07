package org.software.testing.integration_test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.software.testing.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class ValidationServiceWithMocksTest {

    @Mock
    private List<Movie> mockMovies;

    @Mock
    private List<User> mockUsers;

    private ValidationService validationService;

    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock behavior for the lists to return non-null iterators
        when(mockMovies.iterator()).thenReturn(Collections.emptyIterator());
        when(mockUsers.iterator()).thenReturn(Collections.emptyIterator());
        // Use mocked lists
        validationService = new ValidationService(mockMovies, mockUsers);
    }

    @Test
    public void testValidateUserIdUniqueness() {
        // Mock user data
        User user1 = new User("Hassan Ali", "12345678X", Collections.emptyList());
        User user2 = new User("Ali Mohamed", "87654321W", Collections.emptyList());
        when(mockUsers.size()).thenReturn(2);
        when(mockUsers.get(0)).thenReturn(user1);
        when(mockUsers.get(1)).thenReturn(user2);
        // Call the method under test
        validationService.validate();

        // Verify interactions
        verify(mockUsers, times(2)).get(anyInt());
    }

    @Test(expected = AppException.class)
    public void testValidateUserIdUniquenessWithDuplicateIds() {
        // Mock user data with duplicate IDs
        User user1 = new User("Hassan Ali", "12345678X", Collections.emptyList());
        User user2 = new User("Ali Mohamed", "12345678X", Collections.emptyList());
        when(mockUsers.size()).thenReturn(2);
        when(mockUsers.get(0)).thenReturn(user1);
        when(mockUsers.get(1)).thenReturn(user2);

        // Call the method under test (should throw an exception)
        validationService.validate();
    }

    @Test
    public void testValidateMovieIdUniqueness() {
        // Mock movie data
        Movie movie1 = new Movie("The Shawshank Redemption", "TSR001", Arrays.asList("Drama"));
        Movie movie2 = new Movie("The Godfather", "TG002", Arrays.asList("Crime", "Drama"));
        when(mockMovies.size()).thenReturn(2);
        when(mockMovies.get(0)).thenReturn(movie1);
        when(mockMovies.get(1)).thenReturn(movie2);

        // Call the method under test
        validationService.validate();

        // Verify interactions
        verify(mockMovies, times(6)).get(anyInt());
    }

    @Test(expected = AppException.class)
    public void testValidateMovieIdUniquenessWithDuplicateIds() {
        // Mock movie data with duplicate IDs
        Movie movie1 = new Movie("The Shawshank Redemption", "TSR001", Arrays.asList("Drama"));
        Movie movie2 = new Movie("The Godfather", "TSR001", Arrays.asList("Crime", "Drama"));
        when(mockMovies.size()).thenReturn(2);
        when(mockMovies.get(0)).thenReturn(movie1);
        when(mockMovies.get(1)).thenReturn(movie2);

        // Call the method under test (should throw an exception)
        validationService.validate();
    }
}