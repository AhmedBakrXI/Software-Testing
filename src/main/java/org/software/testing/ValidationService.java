package org.software.testing;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService implements MovieValidator, UserValidator {
    private static final Pattern USER_ID_PATTERN = Pattern.compile("^\\d{8}[A-Z]?$");
    private final List<Movie> movies;
    private final List<User> users;

    public ValidationService(List<Movie> movies, List<User> users) {
        this.movies = movies;
        this.users = users;
    }

    public void validate() {
        validateUserIdUniqueness();
        for (User user : users) {
            validateUser(user);
        }

        validateMovieIdUniqueness();
        for (Movie movie : movies) {
            validateMovie(movie);
        }
    }

    @Override
    public void validateUser(User user) {
        validateUsername(user.getName());
        validateUserId(user.getId());
        for (String movieId : user.getFavouriteMovieIds()) {
            validateMovieFound(movieId);
        }
    }

    private void validateMovieFound(String movieId) {
        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AppException("Movie not found", ErrorCode.MOVIE_NOT_FOUND_ERROR);
        }
    }

    @Override
    public void validateMovie(Movie movie) {
        validateMovieId(movie.getId(), movie.getTitle());
        validateMovieTitle(movie.getTitle());
        validateMovieGenre(movie.getGenres());
    }

    private void validateUsername(String name) {
        if (name == null || name.isEmpty()) {
            throw new AppException("User name is empty", ErrorCode.USER_NAME_ERROR);
        }

        validateNameOrTitle(name, true);
    }

    private void validateUserId(String id) {
        checkIdRules(id);
    }

    private void checkIdRules(String id) {
        if (id.length() != 9) {
            throw new AppException("User id length is invalid", ErrorCode.USER_ID_LENGTH_ERROR);
        }
        Matcher matcher = USER_ID_PATTERN.matcher(id);
        if (!matcher.matches()) {
            throw new AppException("User id is invalid", ErrorCode.USER_ID_ERROR);
        }
    }

    private void validateUserIdUniqueness() {
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (users.get(i).getId().equals(users.get(j).getId())) {
                    throw new AppException("User id is not unique", ErrorCode.USER_ID_ERROR);
                }
            }
        }
    }

    private void validateMovieId(String movieId, String title) {
        String[] words = title.split(" ");
        StringBuilder idLetters = new StringBuilder();
        for (String word : words) {
            idLetters.append(Character.toUpperCase(word.trim().charAt(0)));
        }

        if (!movieId.startsWith(idLetters.toString())) {
            throw new AppException("Movie id does not match title", ErrorCode.MOVIE_ID_LETTERS_ERROR);
        }
    }

    private void validateMovieIdUniqueness() {
        for (int i = 0; i < movies.size(); i++) {
            String lastThreeDigitsI = movies.get(i).getId().substring(movies.get(i).getId().length() - 3);
            for (int j = i + 1; j < movies.size(); j++) {
                String lastThreeDigitsJ = movies.get(j).getId().substring(movies.get(j).getId().length() - 3);
                if (lastThreeDigitsI.equals(lastThreeDigitsJ)) {
                    throw new AppException("Last three digits of movie id are not unique", ErrorCode.MOVIE_ID_UNIQUE_ERROR);
                }
            }
        }
    }

    private void validateMovieTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new AppException("Movie title is empty", ErrorCode.MOVIE_TITLE_ERROR);
        }

        validateNameOrTitle(title, false);
    }

    private void validateNameOrTitle(String nameOrTitle, boolean isUser) {
        if (nameOrTitle == null || nameOrTitle.isEmpty()) {
            throw new AppException("Name or title is empty", ErrorCode.USER_NAME_ERROR);
        }

        String[] words = nameOrTitle.split(" ");
        for (String word : words) {
            if (!Character.isUpperCase(word.charAt(0))) {
                if (isUser) {
                    throw new AppException("Name or title is not capitalized", ErrorCode.USER_NAME_ERROR);
                } else {
                    throw new AppException("Name or title is not capitalized", ErrorCode.MOVIE_TITLE_ERROR);
                }
            }
        }
    }

    private void validateMovieGenre(List<String> genres) {
        if (genres == null || genres.isEmpty()) {
            throw new AppException("Movie genre is empty", ErrorCode.MOVIE_GENRE_ERROR);
        }
    }
}
