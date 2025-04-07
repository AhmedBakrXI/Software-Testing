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
         */
        protected User createUser(String name, String id) {
            User user = new User(name, id, new ArrayList<>());
            users.add(user);
            return user;
        }

        /**
         * Helper method to run a piece of code and assert that an AppException is thrown
         * with the expected error code and message.
         */
        protected void assertValidationException(Runnable runnable, ErrorCode expectedErrorCode, String expectedMessage) {
            try {
                runnable.run();
                fail("Expected AppException was not thrown.");
            } catch (AppException e) {
                assertEquals(expectedErrorCode, e.getErrorCode());
                assertEquals(expectedMessage, e.getMessage());
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
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUsernameIsValidTwoWordsWithOneSpace() {
            String name = "Eslam Mohamed";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUsernameIsValidThreeWordsWithTwoSpaces() {
            String name = "Eslam Mohamed marzouk";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validateUser(user);
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameIsNull() {
            String name = null;
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameIsEmpty() {
            String name = "";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameIsSpacesOnly() {
            String name = "     ";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameStartsWithSpace() {
            String name = " Eslam";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameContainsDigit() {
            String name = "Eslam3 Mohamed";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserNameError_whenUsernameContainsPunctuation() {
            String name = "Eslam, Mohamed";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_NAME_ERROR,
                    "User Name " + name + " is wrong"
            );
        }

        @Test
        public void shouldPass_whenUsernameIsOneCharacter() {
            String name = "E";
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUsernameIsVeryLong() {
            String name = "A".repeat(255);
            User user = createUser(name, SIMPLE_VALID_USER_ID);
            validationService.validateUser(user);
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
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUserIdIsValidEightDigitsPlusLowercaseLetter() {
            String id = "12345678a";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUserIdIsValidAllZeros() {
            String id = "00000000Z";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validateUser(user);
        }

        @Test
        public void shouldPass_whenUserIdIsNineDigits() {
            String id = "123456789";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            validationService.validateUser(user);
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsEmpty() {
            String id = "";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsEightDigitsOnly() {
            String id = "12345678";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdStartsWithLetter() {
            String id = "A23456789";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdHasWrongLengthMoreThanNineDigits() {
            String id = "12345678901";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdContainsSpace() {
            String id = "1234 678A";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdContainsSpecialChar() {
            String id = "12345678@";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicateValidAndDuplicatedUserNames() {
            String id = "12345678A";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            User userDuplicate = createUser(SIMPLE_VALID_USER_NAME, id);
            assertValidationException(
                    () -> validationService.validateUser(user),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );

        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicateValidAndDifferentUserNames() {
            String id = "12345678A";
            User user = createUser(SIMPLE_VALID_USER_NAME, id);
            User userDuplicate = createUser("Ahmed", id);
            assertValidationException(
                    () -> validationService.validateUser(userDuplicate),
                    ErrorCode.USER_ID_ERROR,
                    "User ID " + id + " is wrong"
            );
        }

        @Test
        public void shouldThrowUserIdError_whenUserIdIsDuplicateInvalid() {
            String id = "1234567";
            createUser(SIMPLE_VALID_USER_NAME, id);
            User userDuplicate = createUser("Ahmed", id);
            assertValidationException(
                    () -> validationService.validateUser(userDuplicate),
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

            validationService.validateUser(user1);
            validationService.validateUser(user2);

            validationService.validate();
        }
    }
}
