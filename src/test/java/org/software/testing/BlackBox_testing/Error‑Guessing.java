package org.software.testing.BlackBox_testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

class ErrorGuessingTest {
    @Test
    void testDuplicateMovieIds() throws Exception {
        Path file = Files.createTempFile("dup_movies", ".txt");
        Files.writeString(file, "001,TitleA\n001,TitleB\n");
        assertThrows(IllegalArgumentException.class,
                     () -> RecommendationService.loadMovies(file.toString()));
    }

    @Test
    void testBlankLinesAndCommas() throws Exception {
        Path file = Files.createTempFile("weird_movies", ".txt");
        Files.writeString(file, "   \n002,TitleC\n, \n");
        var movies = RecommendationService.loadMovies(file.toString());
        assertTrue(movies.stream().anyMatch(m -> m.getId().equals("002")));
    }
}