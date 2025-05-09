package org.software.testing.blackbox_test;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.software.testing.User;
import org.software.testing.ValidationService;
import org.software.testing.AppException;
import org.software.testing.ErrorCode;
import org.software.testing.Movie;



@RunWith(Enclosed.class)
public abstract class EquivalencePartitioningTest {
   
    /**
     * Abstract base class that provides the common setup and helper methods.
     */
    public static abstract class AbstractValidationServiceTest {
        protected ValidationService validationService;
        protected List<Movie> movies;
        protected List<User> users;

        @Before
        public void setUp() {
            movies = new ArrayList<>();
            users = new ArrayList<>();
            validationService = new ValidationService(movies, users);
        }

        /**
         * Helper to create a new User and add it to the user list.
         *
         * @param name The name of the user.
         * @param id   The ID of the user.
         */
        protected void createUser(String name, String id) {
            User user = new User(name, id, new ArrayList<>());
            users.add(user);
        }

        /**
         * Helper to create a new Movie and add it to the movie list.
         *
         * @param title  The title of the movie.
         * @param id     The ID of the movie.
         * @param genres The genres of the movie.
         */
        protected void createMovie(String title, String id, List<String> genres) {
            Movie movie = new Movie(title, id, genres);
            movies.add(movie);
        }

        /**
         * Helper method to run a piece of code and assert that an AppException is thrown
         * with the expected error code and message.
         *
         * @param runnable          The code to run.
         * @param expectedErrorCode The expected error code.
         * @param expectedMessage   The expected error message.
         */
        protected void assertValidationException(Runnable runnable, ErrorCode expectedErrorCode, String expectedMessage) {
            try {
                runnable.run();
                fail("Expected AppException was not thrown.");
            } catch (AppException e) {
                assertEquals(expectedMessage, e.getMessage());
                assertEquals(expectedErrorCode, e.getErrorCode());
            }
        }
    }

    /**
     * Test class for username validations.
     */
    public static class UserNameValidationTest extends AbstractValidationServiceTest {
        private static final String SIMPLE_VALID_USER_ID = "00000000Z";

        // --- Private Helpers for UserNameValidationTest ---

        /**
         * Helper method to run user validation and expect it to pass.
         * @param name The name of the user.
         */
        private void runUserValidationExpectPass(String name) {
                createUser(name, SIMPLE_VALID_USER_ID);
                validationService.validate();
        }

        /**
         * Helper method to run user validation and expect it to throw an exception = USER_NAME_ERROR.
         * @param name The name of the user.
         */
        private void runUserValidationExpectError(String name) {
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(() -> validationService.validate(), ErrorCode.USER_NAME_ERROR, "User Name " + name + " is wrong");
        }

        // --- Test Methods ---
            // class 1 :It must be Alphabetic characters and Spaces. the name should not start with space--> truevalidation .
        @Test
        public void shouldPass_whenUsernameIsValid() {
            String name = "Eslam";
            runUserValidationExpectPass(name);
        }
        @Test
        public void shouldPass_whenUsernameIsValidAlphabeticCharsandSpaces() {
            String name = "Eslam Mohamed";
            runUserValidationExpectPass(name);
        }


            // class 2 : the name should  start with space--> falsevalidation .
        @Test
        public void shouldThrowUserNameError_whenUsernameStartsWithSpace() {
            String name = " Eslam";
            runUserValidationExpectError(name);
        }

             // class 3 : the name must have numeric or special character--> falsevalidation .
        @Test
        public void shouldThrowUserNameError_whenUsernameContainsDigit() {
            String name = "Eslam3 Mohamed";
            runUserValidationExpectError(name);
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameContainsPunctuation() {
            String name = "Eslam, Mohamed";
            runUserValidationExpectError(name);
        }
    }

    /**
     * Test class for user ID validations.
     */
    public static class UserIdValidationTest extends AbstractValidationServiceTest {
        private static final String SIMPLE_VALID_USER_NAME = "Eslam";

        // --- Private Helpers for UserIdValidationTest ---
        /**
         * Helper method to run user ID validation and expect it to pass.
         * @param id The ID of the user.
         */
        private void runUserIdValidationExpectPass(String id) {
            createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validate();
        }

        /**
         * Helper method to run user ID validation and expect it to throw an exception = USER_ID_ERROR.
         * @param id The ID of the user.
         */
        private void runUserIdValidationExpectError(String id) {
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(() -> validationService.validate(), ErrorCode.USER_ID_ERROR, "User ID " + id + " is wrong");
        }

        // --- Test Methods ---
            //class 1: must be Alphanumeric characters of exact length of 9 characters. It should start with numbers and might End with only one Alphabetic character The users’ ids must be unique. 
        @Test
        public void shouldPass_whenUserIdIsValidAlphanumericNinechars() {
            String id = "12345678A";
            runUserIdValidationExpectPass(id);
        }


            //class 2: must be Alphanumeric characters less than 9 characters.
        @Test
        public void shouldThrowUserIdError_whenUserIdHasWrongLengthLessThanNineDigits() {
            String id = "12345678";
            runUserIdValidationExpectError(id);
        }

            //class 3: must be Alphanumeric characters more than 9 characters.
        @Test
        public void shouldThrowUserIdError_whenUserIdHasWrongLengthMoreThanNineDigits() {
            String id = "12345678901";
            runUserIdValidationExpectError(id);
        }

            //class 4: must be Alphanumeric characters more than 9 characters.
        @Test
        public void shouldThrowUserIdError_whenUserIdContainsSpecialChar() {
            String id = "12345678@";
            runUserIdValidationExpectError(id);
        }

            //class 5: must be starts with non numeric.
        @Test
        public void shouldThrowUserIdError_whenUserIdStartsWithNonNumeric() {
            String id = "A23456789";
            runUserIdValidationExpectError(id);
        }

            //class 6: must be ends with non alphapetic.
        @Test
        public void shouldThrowUserIdError_whenUserIdEndsWithNonAlphapetic() {
            String id = "423456789";
            runUserIdValidationExpectError(id);
        }

            //class 7: must be duplicate.
        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicate() {
            String id = "12345678A";
            createUser(SIMPLE_VALID_USER_NAME, id);
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(() -> validationService.validate(), ErrorCode.USER_ID_ERROR, "User ID " + id + " is not unique");
        }
    }


    /**
     * Test class for overall (integration) validation of multiple users.
     */
    public static class UserIntegrationTest extends AbstractValidationServiceTest {
        @Test
        public void shouldPass_whenAllUsersAreValid() {
            String id1 = "12345678A";
            String id2 = "87654321B";
            User user1 = new User("Eslam", id1, new ArrayList<>());
            User user2 = new User("Ahmed", id2, new ArrayList<>());
            users.add(user1);
            users.add(user2);
            validationService.validate();
        }
    }

    /**
     * Test class for movie title validations.
     */
    public static class MovieTitleValidationTest extends AbstractValidationServiceTest {
        private static final List<String> VALID_GENRES = List.of("Action");

        // --- Private Helpers for MovieTitleValidationTest ---

        /**
         * Helper method to run movie title validation and expect it to pass.
         * @param title The title of the movie.
         * @param id    The ID of the movie.
         */
        private void runMovieTitleValidationExpectPass(String title, String id) {
            createMovie(title, id, VALID_GENRES);
            validationService.validate();
        }

        /**
         * Helper method to run movie title validation and expect it to throw an exception = MOVIE_TITLE_ERROR.
         * @param title The title of the movie.
         * @param id    The ID of the movie.
         */
        private void runMovieTitleValidationExpectError(String title, String id) {
            createMovie(title, id, VALID_GENRES);
            assertValidationException(() -> validationService.validate(), ErrorCode.MOVIE_TITLE_ERROR, "Movie Title " + title + " is wrong");
        }

        // --- Test Methods ---
            // class 1: the movie title should be such as every word in the title starts with a capital letter .
        @Test
        public void shouldPass_whenMovieTitleHasWordsAllUppercaseStart() {
            String title = "Spider Man Into The Spider Verse";
            String id = "SMITSV002";
            runMovieTitleValidationExpectPass(title, id);
        }

            // class 2: the movie title should be such as any word in the title not starts with a capital letter .
        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleIsWordLowercaseStart() {
            String title = "inception";
            String id = "I002";
            runMovieTitleValidationExpectError(title, id);
        }
        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleIsEmpty() {
            String title = "";
            String id = "I003";
            runMovieTitleValidationExpectError(title, id);
        }
        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleHasAWordStartingWithDigit() {
            String title = "Blade Runner 2049";
            String id = "BR005";
            runMovieTitleValidationExpectError(title, id);
        }
    }

    /**
     * Test class for movie ID validations.
     */
    public static class MovieIdValidationTest extends AbstractValidationServiceTest {
        private static final List<String> VALID_GENRES = List.of("Action");

        // --- Private Helpers for MovieIdValidationTest ---

        /**
         * Helper method to run movie ID validation and expect it to pass.
         * @param title The title of the movie.
         * @param id    The ID of the movie.
         */
        private void runMovieIdValidationExpectPass(String title, String id) {
            createMovie(title, id, VALID_GENRES);
            validationService.validate();
        }

        /**
         * Helper method to run movie ID validation and expect it to throw an exception = MOVIE_ID_LETTERS_ERROR.
         * @param title The title of the movie.
         * @param id    The ID of the movie.
         */
        private void runMovieIdValidationExpectError(String title, String id) {
            createMovie(title, id, VALID_GENRES);
            assertValidationException(() -> validationService.validate(), ErrorCode.MOVIE_ID_LETTERS_ERROR, "Movie Id letters " + id + " are wrong");
        }

        // --- Test Methods ---
            // class 1: it must contain all the capital letters in the title followed by 3 unique numbers
        @Test
        public void shouldPass_whenMovieIdHasAllCapLettersAndThreeDigits() {
            String title = "Fight Club";
            String id = "FC001";
            runMovieIdValidationExpectPass(title, id);
        }
            // class 2: it must has lowercase letter instead of capital followed by 3 unique numbers
        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdHasLowercaseInsteadOfTitleCapLetters() {
            String title = "Fight Club";
            String id = "fc001";
            runMovieIdValidationExpectError(title, id);
        }
            // class 3: it must less than the capital letters in the title followed by 3 unique numbers
        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdIsMissingSomeTitleLetters() {
            String title = "Spider Man Into The Spider Verse";
            String id = "SM002";
            runMovieIdValidationExpectError(title, id);
        }

            // class 4: it must more than the capital letters in the title followed by 3 unique numbers
        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdIsMoreThanTitleLetters() {
            String title = "Spider Man Into The Spider Verse";
            String id = "SMITSVOP002";
            runMovieIdValidationExpectError(title, id);
        }

            // class 5: it must  letters in the title followed by less than 3 digit numbers
        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdIsLessDigits() {
            String title = "Interstellar";
            String id = "I02";
            runMovieIdValidationExpectError(title, id);
        }
             // class 6: it must more than the capital letters in the title followed by more than 3 digit numbers
        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdIsMoreDigits() {
            String title = "Inception";
            String id = "I00001";
            runMovieIdValidationExpectError(title, id);
        }
    }

    /**
     * Test class for movie id uniqueness validations.
     */
    public static class MovieIdUniquenessTest extends AbstractValidationServiceTest {
        private static final List<String> VALID_GENRES = List.of("Action");

        // class 1: it must be unique.
        @Test
        public void shouldPass_whenTwoMoviesHaveDifferentLettersAndDifferentNumbers() {
            String title1 = "Fight Club";
            String id1 = "FC123";
            createMovie(title1, id1, VALID_GENRES); // First movie

            String title2 = "Interstellar";
            String id2 = "I345";
            createMovie(title2, id2, VALID_GENRES); // Second movie, different id

            validationService.validate();
        }

        // class 2: it mustn't be unique.
        @Test
        public void shouldThrowMovieIdUniqueError_whenTwoMoviesHaveDifferentLettersButSameNumbers() {
            String id = "FC999";
            createMovie("Fight Club", id, VALID_GENRES);      // First movie
            createMovie("Blade Runner", "BR999", VALID_GENRES); // Second movie, same number part

            assertValidationException(() -> validationService.validate(), ErrorCode.MOVIE_ID_UNIQUE_ERROR, "Movie Id numbers " + id + " aren’t unique");
        }

        @Test
        public void shouldThrowMovieIdUniqueError_whenTwoMoviesHaveExactlySameId() {
            String id = "FC123";
            createMovie("Fight Club", id, VALID_GENRES);    // First movie
            createMovie("Fight Club2", id, VALID_GENRES);     // Second movie, same id

            assertValidationException(() -> validationService.validate(), ErrorCode.MOVIE_ID_UNIQUE_ERROR, "Movie Id numbers " + id + " aren’t unique");
        }
    }

    /**
     * Test class for movie genre validations.
     */
    public static class MovieGenreValidationTest extends AbstractValidationServiceTest {
        private static final String SIMPLE_VALID_MOVIE_ID = "FC001";
        private static final String SIMPLE_VALID_MOVIE_TITLE = "Fight Club";

        // --- Private Helpers for MovieGenreValidationTest ---

        private void runMovieGenreValidationExpectPass(List<String> genres) {
            createMovie(MovieGenreValidationTest.SIMPLE_VALID_MOVIE_TITLE, MovieGenreValidationTest.SIMPLE_VALID_MOVIE_ID, genres);
            validationService.validate();
        }

        private void runMovieGenreValidationExpectError(List<String> genres) {
            createMovie(MovieGenreValidationTest.SIMPLE_VALID_MOVIE_TITLE, MovieGenreValidationTest.SIMPLE_VALID_MOVIE_ID, genres);
            assertValidationException(() -> validationService.validate(), ErrorCode.MOVIE_GENRE_ERROR, "Movie genre is empty");
        }

        // --- Test Methods ---
            // class 1: it must be exist at least one genre.
        @Test
        public void shouldPass_whenMovieHasAtLeastOneGenre() {
            List<String> genres = List.of("Action", "Drama");
            runMovieGenreValidationExpectPass(genres);
        }
            // class 2: it mustn't exist any genre.
        @Test
        public void shouldThrowMovieGenreError_whenMovieHasEmptyGenreList() {
            List<String> genres = new ArrayList<>();
            runMovieGenreValidationExpectError(genres);
        }
    }
}