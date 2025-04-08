package org.software.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Enclosed.class)
public abstract class ValidationServiceTest {

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
            users  = new ArrayList<>();
            validationService = new ValidationService(movies, users);
        }

        /**
         * Helper to create a new User and add it to the user list.
         *
         * @param name The name of the user.
         * @param id The ID of the user.
         *
         * @return The created User object.
         */
        protected User createUser(String name, String id) {
            User user = new User(name, id, new ArrayList<>());
            users.add(user);
            return user;
        }

        /**
         * Helper to create a new Movie and add it to the movie list.
         *
         * @param title The title of the movie.
         * @param id The ID of the movie.
         * @param genres The genres of the movie.
         *
         * @return The created Movie object.
         */
        protected Movie createMovie(String title, String id, List<String> genres) {
            Movie movie = new Movie(title, id, genres);
            movies.add(movie);
            return movie;
        }

        /**
         * Helper method to run a piece of code and assert that an AppException is thrown
         * with the expected error code and message.
         *
         * @param runnable The code to run.
         * @param expectedErrorCode The expected error code.
         * @param expectedMessage The expected error message.
         *
         * @throws AppException if the expected exception is not thrown.
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

        @Test
        public void shouldPass_whenUsernameIsValidSingleWord() {
            String name = "Eslam";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUsernameIsValidSingleWordStartsWithLowercase() {
            String name = "eslam";
            createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenUsernameIsValidTwoWordsWithOneSpace() {
            String name = "Eslam Mohamed";
            createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenUsernameIsValidThreeWordsWithTwoSpaces() {
            String name = "Eslam Mohamed marzouk";
            createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validate();
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameIsNull() {
            String name = null;
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameIsEmpty() {
            String name = "";
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameIsSpacesOnly() {
            String name = "     ";
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameStartsWithSpace() {
            String name = " Eslam";
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameContainsDigit() {
            String name = "Eslam3 Mohamed";
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameContainsPunctuation() {
            String name = "Eslam, Mohamed";
            createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldPass_whenUsernameIsOneCharacter() {
            String name = "E";
            createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenUsernameIsVeryLong() {
            String name = "A".repeat(255);
            createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validate();
        }
    }

    /**
     * Test class for user ID validations.
     */
    public static class UserIdValidationTest extends AbstractValidationServiceTest {
        private static final String SIMPLE_VALID_USER_NAME = "Eslam";

        @Test
        public void shouldPass_whenUserIdIsValidEightDigitsPlusLetter() {
            String id = "12345678A";
            createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenUserIdIsValidEightDigitsPlusLowercaseLetter() {
            String id = "12345678a";
            createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenUserIdIsValidAllZeros() {
            String id = "00000000Z";
            createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenUserIdIsNineDigits() {
            String id = "123456789";
            createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validate();
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsEmpty() {
            String id = "";
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsEightDigitsOnly() {
            String id = "12345678";
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdStartsWithLetter() {
            String id = "A23456789";
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdHasWrongLengthMoreThanNineDigits() {
            String id = "12345678901";
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdContainsSpace() {
            String id = "1234 678A";
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdContainsSpecialChar() {
            String id = "12345678@";
            createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicateValidAndDuplicatedUserNames() {
            String id = "12345678A";
            createUser(SIMPLE_VALID_USER_NAME, id); // First user
            createUser(SIMPLE_VALID_USER_NAME, id); // Second user, same name and id
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );

        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicateValidAndDifferentUserNames() {
            String id = "12345678A";
            createUser(SIMPLE_VALID_USER_NAME, id); // First user
            createUser("Ahmed", id); // Second user, different name but same id
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicateInvalid() {
            String id = "1234567";
            createUser(SIMPLE_VALID_USER_NAME, id);
            createUser("Ahmed", id);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
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

        @Test
        public void shouldPass_whenMovieTitleIsSingleWordUppercase() {
            String title = "Inception";
            String id = "I001";
            createMovie(title, id, VALID_GENRES);
            validationService.validate();
        }

        @Test
        public void shouldPass_whenMovieTitleHasSixWordsAllUppercaseStart(){
            String tile = "Spider Man Into The Spider Verse";
            String id = "SMITSV002";
            createMovie(tile, id, VALID_GENRES);
            validationService.validate();
        }

        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleIsSingleWordLowercase() {
            String title = "inception";
            String id = "I002";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_TITLE_ERROR,
                    "Movie Title " + title + " is wrong"
            );
        }

        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleHasLowercaseStartsAndOneUpperCaseStart(){
            String title = "spider man into The spider verse";
            String id = "SMITSV003";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_TITLE_ERROR,
                    "Movie Title " + title + " is wrong"
            );
        }

        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleIsEmpty() {
            String title = "";
            String id = "I003";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_TITLE_ERROR,
                    "Movie Title " + title + " is wrong"
            );
        }

        @Test
        public void shouldPass_whenMovieTitleHasSpecialCharactersInWords() {
            String title = "Blade Runner-2049";
            String id = "BR004";
            createMovie(title, id, VALID_GENRES);
            validationService.validate();
        }

        @Test
        public void shouldThrowMovieTitleError_whenMovieTitleHasAWordStartingWithDigit() {
            String title = "Blade Runner 2049";
            String id = "BR005";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_TITLE_ERROR,
                    "Movie Title " + title + " is wrong"
            );
        }

    }

    /**
     * Test class for movie ID validations.
     */
    public static class MovieIdValidationTest extends AbstractValidationServiceTest{
        private static final List<String> VALID_GENRES = List.of("Action");

        @Test
        public void shouldPass_whenMovieIdHasAllCapLettersAndThreeDigits() {
            String title = "Fight Club";
            String id = "FC001";
            createMovie(title, id, VALID_GENRES);
            validationService.validate();
        }

        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdHasLowercaseInsteadOfTitleCapLetters() {
            String title = "Fight Club";
            String id = "fc001";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_ID_LETTERS_ERROR,
                    "Movie Id letters " + id + " are wrong"
            );
        }

        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdIsMissingSomeTitleLetters() {
            String title = "Spider Man Into The Spider Verse";
            String id = "SM002";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_ID_LETTERS_ERROR,
                    "Movie Id letters " + id + " are wrong"
            );
        }

        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdHasOnlyTwoDigits(){
            String title = "Interstellar";
            String id = "I02";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_ID_LETTERS_ERROR,
                    "Movie Id letters " + id + " are wrong"
            );
        }

        @Test
        public void shouldThrowMovieIdLettersError_whenMovieIdHasFiveDigits() {
            String title = "Inception";
            String id = "I00001";
            createMovie(title, id, VALID_GENRES);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_ID_LETTERS_ERROR,
                    "Movie Id letters " + id + " are wrong"
            );
        }
    }

    /**
     * Test class for movie id uniqueness validations.
     */
    public static class MovieIdUniquenessTest extends AbstractValidationServiceTest {
        private static final List<String> VALID_GENRES = List.of("Action");

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

        @Test
        public void shouldPass_whenTwoMoviesHaveSameLettersButDifferentNumbers() {
            String title1 = "Spider Man";
            String id1 = "SM123";
            createMovie(title1, id1, VALID_GENRES); // First movie

            String title2 = "Spider Man2";
            String id2 = "SM456";
            createMovie(title2, id2, VALID_GENRES); // Second movie, different id

            validationService.validate();
        }

        @Test
        public void shouldThrowMovieIdUniqueError_whenTwoMoviesHaveDifferentLettersButSameNumbers() {
            String title1 = "Fight Club";
            String id1 = "FC999";
            createMovie(title1, id1, VALID_GENRES); // First movie

            String title2 = "Blade Runner";
            String id2 = "BR999";
            createMovie(title2, id2, VALID_GENRES); // Second movie, same id's numbers but different letters

            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_ID_UNIQUE_ERROR,
                    "Movie Id numbers " + id1 + " aren’t unique"
            );
        }

        @Test
        public void shouldThrowMovieIdUniqueError_whenTwoMoviesHaveExactlySameId() {
            String title1 = "Fight Club";
            String id1 = "FC123";
            createMovie(title1, id1, VALID_GENRES); // First movie

            String title2 = "Fight Club2";
            String id2 = "FC123";
            createMovie(title2, id2, VALID_GENRES); // Second movie, same id

            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_ID_UNIQUE_ERROR,
                    "Movie Id numbers " + id1 + " aren’t unique"
            );
        }
    }

    /**
     * Test class for movie genre validations.
     */
    public static class MovieGenreValidationTest extends AbstractValidationServiceTest{
        private static final String SIMPLE_VALID_MOVIE_ID = "FC001";
        private static final String SIMPLE_VALID_MOVIE_TITLE = "Fight Club";

        @Test
        public void shouldPass_whenMovieHasMultipleGenres() {
            List<String> genres = List.of("Action", "Drama");
            createMovie(SIMPLE_VALID_MOVIE_TITLE, SIMPLE_VALID_MOVIE_ID, genres);
            validationService.validate();
        }

        @Test
        public void shouldThrowMovieGenreError_whenMovieHasEmptyGenreList() {
            List<String> genres = new ArrayList<>();
            createMovie(SIMPLE_VALID_MOVIE_TITLE, SIMPLE_VALID_MOVIE_ID, genres);
            assertValidationException(
                    () -> validationService.validate(),
                    ErrorCode.MOVIE_GENRE_ERROR,
                    "Movie genre is empty"
            );
        }
    }

}
