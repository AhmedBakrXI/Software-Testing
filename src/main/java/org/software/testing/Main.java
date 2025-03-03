package org.software.testing;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private final static String MOVIE_FILE_PATH = "movies.txt";
    private final static String USER_FILE_PATH = "users.txt";
    private final static String RECOMMENDATIONS_FILE_PATH = "recommendations.txt";
    private final static String ERROR_FILE_PATH = "errors.txt";

    public static void main(String[] args) throws IOException {
        List<User> users = FileReader.readUsers(USER_FILE_PATH);
        List<Movie> movies = FileReader.readMovies(MOVIE_FILE_PATH);

        try {
            ValidationService validationService = new ValidationService(movies, users);
            validationService.validate();

            RecommendationStrategy recommendationStrategy = new GenreBasedRecommendation();
            MovieRecommendationService movieRecommendationService = new MovieRecommendationService(recommendationStrategy);
            Map<User, List<Movie>> recommendations = movieRecommendationService.generateRecommendations(users, movies);

            String output = buildOutput(recommendations);
            FileWriter.writeToFile(RECOMMENDATIONS_FILE_PATH, output);
        } catch (AppException e) {
            System.out.println(e.getMessage() + ", Id: " + e.getErrorCode().getCode());
            e.printStackTrace();
            boolean isWritten = FileWriter.writeToFile(ERROR_FILE_PATH, "ERROR: " + e.getMessage());
            if (!isWritten) {
                System.out.println("Error written to file: " + ERROR_FILE_PATH);
            }
            System.exit(e.getErrorCode().getCode());
        }
    }

    private static String buildOutput(Map<User, List<Movie>> recommendations) {
        StringBuilder outputBuilder = new StringBuilder();
        for (Map.Entry<User, List<Movie>> entry : recommendations.entrySet()) {
            User user = entry.getKey();
            List<Movie> recommendedMovies = entry.getValue();
            outputBuilder.append(user.getName()).append(", ").append(user.getId()).append("\n");
            for (int index = 0; index < recommendedMovies.size(); index++) {
                Movie movie = recommendedMovies.get(index);
                outputBuilder.append(movie.getTitle());
                if (index != recommendedMovies.size() - 1) {
                    outputBuilder.append(", ");
                }
            }
            outputBuilder.append("\n");
        }
        return outputBuilder.toString();
    }
}