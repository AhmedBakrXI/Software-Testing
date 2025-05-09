package org.software.testing.white_box;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.software.testing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ValidationServiceTest {

    private ValidationService validationService;
    private List<Movie> movies;
    private List<User> users;

    @Before
    public void setUp() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
        validationService = new ValidationService(movies, users);
    }

    @Test
    public void testValidateUser_ValidUser() {
        User user = new User("Ahmed", "12345678A", List.of("TSR001"));
        users.add(user);
        movies.add(new Movie("The Shawshank Redemption", "TSR001", List.of("Drama")));
        validationService.validate();
    }

    @Test
    public void testValidateUser_InvalidUserName() {
        User user = new User("ahmed", "12345678A", List.of("TSR001"));
        users.add(user);
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateUser_InvalidUserId() {
        User user = new User("Ahmed", "1234567", List.of("TSR001"));
        users.add(user);
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateUser_NonExistentMovie() {
        User user = new User("Ahmed", "12345678A", List.of("INVALID001"));
        users.add(user);
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateMovie_ValidMovie() {
        movies.add(new Movie("The Shawshank Redemption", "TSR001", List.of("Drama")));
        validationService.validate();
    }

    @Test
    public void testValidateMovie_InvalidMovieId() {
        movies.add(new Movie("The Shawshank Redemption", "TSR01", List.of("Drama")));
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateMovie_InvalidMovieTitle() {
        movies.add(new Movie("the shawshank redemption", "TSR001", List.of("Drama")));
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateMovie_EmptyGenres() {
        movies.add(new Movie("The Shawshank Redemption", "TSR001", new ArrayList<>()));
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateUserIdUniqueness_DuplicateIds() {
        users.add(new User("Ahmed", "12345678A", List.of("TSR001")));
        users.add(new User("Ali", "12345678A", List.of("TG002")));
        assertThrows(AppException.class, validationService::validate);
    }

    @Test
    public void testValidateMovieIdUniqueness_DuplicateLastThreeDigits() {
        movies.add(new Movie("The Shawshank Redemption", "TSR001", List.of("Drama")));
        movies.add(new Movie("The Godfather", "TG001", List.of("Crime")));
        assertThrows(AppException.class, validationService::validate);
    }
    @Test
    public void testValidateNameOrTitle_NullOrEmpty() {
        ValidationService validationService = new ValidationService(List.of(), List.of());

        // Test with null name or title
        AppException exception1 = assertThrows(AppException.class,
                () -> validationService.validateNameOrTitle(null, true));
        assertEquals("Name or title is empty", exception1.getMessage());

        // Test with empty name or title
        AppException exception2 = assertThrows(AppException.class,
                () -> validationService.validateNameOrTitle("", true));
        assertEquals("Name or title is empty", exception2.getMessage());
    }
}