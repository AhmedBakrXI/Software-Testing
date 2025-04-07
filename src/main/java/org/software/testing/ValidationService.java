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
        validateUsername(user.name());
        validateUserId(user.id());
        for (String movieId : user.favouriteMovieIds()) {
            validateMovieFound(movieId);
        }
    }

    @Override
    public void validateMovie(Movie movie) {
        validateMovieId(movie.id(), movie.title());
        validateMovieTitle(movie.title());
        validateMovieGenre(movie.genres());
    }

    private void validateMovieFound(String movieId) {
        boolean found = false;
        for (Movie movie : movies) {
            if (movie.id().equals(movieId)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AppException("Movie " + movieId + " not found", ErrorCode.MOVIE_NOT_FOUND_ERROR);
        }
    }

    private void validateUsername(String name) {
        if (name == null || name.isEmpty() || name.startsWith(" ") || !name.matches("[A-Za-z ]+")) {
            throw new AppException("User Name " + name + " is wrong", ErrorCode.USER_NAME_ERROR);
        }
        validateNameOrTitle(name, true);
    }

    private void validateUserId(String id) {
        checkIdRules(id);
    }

    private void checkIdRules(String id) {
        Matcher matcher = USER_ID_PATTERN.matcher(id);
        if (id.length() != 9 || !matcher.matches()) {
                throw new AppException("User ID "+ id +" is wrong", ErrorCode.USER_ID_ERROR);
        }
    }

    private void validateUserIdUniqueness() {
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (users.get(i).id().equals(users.get(j).id())) {
                    throw new AppException("User ID "+ users.get(i).id() + " is not unique"
                            , ErrorCode.USER_ID_ERROR);
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

        if (!movieId.startsWith(idLetters.toString()) || movieId.length() != (idLetters.length() + 3)) {
            throw new AppException("Movie ID letters "+ movieId +" are wrong", ErrorCode.MOVIE_ID_LETTERS_ERROR);
        }
    }

    private void validateMovieIdUniqueness() {
        for (int i = 0; i < movies.size(); i++) {
            String lastThreeDigitsI = movies.get(i).id().substring(movies.get(i).id().length() - 3);
            for (int j = i + 1; j < movies.size(); j++) {
                String lastThreeDigitsJ = movies.get(j).id().substring(movies.get(j).id().length() - 3);
                if (lastThreeDigitsI.equals(lastThreeDigitsJ)) {
                    throw new AppException("Movie ID numbers " + movies.get(i).id() + " aren't unique"
                            , ErrorCode.MOVIE_ID_UNIQUE_ERROR);
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
                    throw new AppException("User Name" + nameOrTitle +" is wrong", ErrorCode.USER_NAME_ERROR);
                } else {
                    throw new AppException("Movie title " + nameOrTitle +" is wrong", ErrorCode.MOVIE_TITLE_ERROR);
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
