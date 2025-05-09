package org.software.testing.BlackBox_testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EquivalencePartitioningTest {
    @ParameterizedTest
    @CsvSource({
        "ABC123XYZ,false",      // valid ID
        "'',true",             // empty string
        "'   ',true",          // whitespace only
        "AB!@#12XY,true",      // illegal chars
        "A1B2C3,true",         // too short
        "AAAAAAAAAA,true"      // too long (10 chars)
    })
    void testUserIdPartitions(String userId, boolean shouldFail) {
        if (shouldFail) {
            assertThrows(IllegalArgumentException.class,
                         () -> RecommendationService.getRecommendations(userId));
        } else {
            var recs = RecommendationService.getRecommendations(userId);
            assertNotNull(recs);
            assertFalse(recs.isEmpty());
        }
    }
}