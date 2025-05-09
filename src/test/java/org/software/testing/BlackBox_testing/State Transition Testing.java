package org.software.testing.BlackBox_testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StateTransitionTest {
    @Test
    void testStateSequences() {
        RecommendationService.resetSystem();
        assertThrows(IllegalStateException.class,
                     () -> RecommendationService.getRecommendations("123456789"));

        // Load users before movies
        RecommendationService.resetSystem();
        RecommendationService.loadUsers("users_bad.txt");
        RecommendationService.loadMovies("movies.txt");
        assertThrows(IllegalStateException.class,
                     () -> RecommendationService.getRecommendations("123456789"));

        // Correct sequence
        RecommendationService.resetSystem();
        RecommendationService.loadMovies("movies.txt");
        RecommendationService.loadUsers("users.txt");
        var recs = RecommendationService.getRecommendations("123456789");
        assertNotNull(recs);
        assertFalse(recs.isEmpty());
    }
}