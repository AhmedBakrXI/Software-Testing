package org.software.testing.blackbox_test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.software.testing.GenreBasedRecommendation;
import org.software.testing.RecommendationStrategy;
import org.software.testing.User;
import org.software.testing.Movie;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class ErrorGuessingTest {
    private final String userId;
    private final boolean shouldFail;

    private final RecommendationStrategy recommendationStrategy = new GenreBasedRecommendation();

    public ErrorGuessingTest(String userId, boolean shouldFail) {
        this.userId = userId;
        this.shouldFail = shouldFail;
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"12345678A", false},  // valid ID
            {"", true},            // empty string
            {"   ", true},         // whitespace only
            {"1234ABCD", true},    // illegal chars
            {"1234567", true},     // too short
            {"1234567890", true}   // too long (10 chars)
        });
    }

    @Test
    public void testUserIdPartitions() {
        User user = new User("Test User", userId, Collections.emptyList());
        List<Movie> movies = Collections.emptyList();

        if (shouldFail) {
            try {
                recommendationStrategy.recommend(user, movies);
                fail("Expected IllegalArgumentException for invalid userId: " + userId);
            } catch (IllegalArgumentException e) {
                // Exception is expected, test passes
            }
        } else {
            List<Movie> recs = recommendationStrategy.recommend(user, movies);
            assertNotNull(recs);
            assertTrue(recs.isEmpty());
        }
    }
}