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
public class DecisionTableTesting {
    private final String userId;
    private final boolean shouldFail;

    private final RecommendationStrategy recommendationStrategy = new GenreBasedRecommendation();

    public DecisionTableTesting(String userId, boolean shouldFail) {
        this.userId = userId;
        this.shouldFail = shouldFail;
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"ABC123XYZ", false},  // valid ID
            {"", true},            // empty string
            {"   ", true},         // whitespace only
            {"AB!@#12XY", true},   // illegal chars
            {"A1B2C3", true},      // too short
            {"AAAAAAAAAA", true}   // too long (10 chars)
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
                
            }
        } else {
            List<Movie> recs = recommendationStrategy.recommend(user, movies);
            assertNotNull(recs);
            assertTrue(recs.isEmpty());
        }
    }
}