package org.software.testing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRecommendationService {
    RecommendationStrategy recommendationStrategy;

    public MovieRecommendationService(RecommendationStrategy recommendationStrategy) {
        this.recommendationStrategy = recommendationStrategy;
    }

    public Map<User, List<Movie>> generateRecommendations(List<User> users, List<Movie> movies) {
        Map<User, List<Movie>> recommendations = new HashMap<>();
        for (User user : users) {
            recommendations.put(user, recommendationStrategy.recommend(user, movies));
        }
        return recommendations;
    }
}
