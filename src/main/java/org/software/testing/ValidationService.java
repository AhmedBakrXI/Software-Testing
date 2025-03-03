package org.software.testing;

public class ValidationService implements MovieValidator, UserValidator {
    @Override
    public boolean validateUser(User user) {
        return false;
    }

    @Override
    public boolean validateMovie(Movie movie) {
        return false;
    }
}
