package toyproject.pyeonsool.repository;

import toyproject.pyeonsool.domain.RecommendStatus;

public interface RecommendedReviewCustomRepository {
    Long getRecommendCountGroupBy(Long reviewId, RecommendStatus status);
}
