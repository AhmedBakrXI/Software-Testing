package org.software.testing;

import java.util.List;

public interface RecommendationStrategy {
    List<Movie> recommend(User user, List<Movie> movies);
}
