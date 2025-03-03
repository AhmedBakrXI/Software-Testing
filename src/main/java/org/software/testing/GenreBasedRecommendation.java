package org.software.testing;

import java.util.ArrayList;
import java.util.List;

public class GenreBasedRecommendation implements RecommendationStrategy {
    @Override
    public List<Movie> recommend(User user, List<Movie> movies) {
        List<Movie> recommendedMovies = new ArrayList<>();
        for (String favouriteMovieId : user.getFavouriteMovieIds()) {
            Movie favouriteMovie = findMovieById(favouriteMovieId, movies);
            if (favouriteMovie != null) {
                addMoviesByGenre(favouriteMovie.getGenres(), movies, recommendedMovies, user.getFavouriteMovieIds());
            }
        }
        return recommendedMovies;
    }

    private Movie findMovieById(String movieId, List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId)) {
                return movie;
            }
        }
        return null;
    }

    private void addMoviesByGenre(List<String> genres, List<Movie> movies, List<Movie> recommendedMovies, List<String> favouriteMovieIds) {
        for (String genre : genres) {
            for (Movie movie : movies) {
                if (movie.getGenres().contains(genre) && !recommendedMovies.contains(movie) && !favouriteMovieIds.contains(movie.getId())) {
                    recommendedMovies.add(movie);
                }
            }
        }
    }
}
