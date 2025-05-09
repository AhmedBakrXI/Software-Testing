package org.software.testing.BlackBox_testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoundaryValueAnalysisTest {
    @ParameterizedTest
    @CsvSource({
        "12,false",   // 2 digits (below boundary)
        "123,true",   // 3 digits (valid)
        "1234,false"  // 4 digits (above boundary)
    })
    void testMovieIdLength(String movieId, boolean valid) {
        if (valid) {
            var movie = RecommendationService.loadMovieById(movieId);
            assertEquals(movieId, movie.getId());
        } else {
            assertThrows(IllegalArgumentException.class,
                         () -> RecommendationService.loadMovieById(movieId));
        }
    }
}