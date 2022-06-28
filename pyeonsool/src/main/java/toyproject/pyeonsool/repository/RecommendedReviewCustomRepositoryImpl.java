package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.RecommendStatus;

import static toyproject.pyeonsool.domain.QRecommendedReview.*;

@RequiredArgsConstructor
public class RecommendedReviewCustomRepositoryImpl implements RecommendedReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long getRecommendCountGroupBy(Long reviewId, RecommendStatus status) {
        return queryFactory.select(recommendedReview.count())
                .from(recommendedReview)
                .where(recommendedReview.review.id.eq(reviewId)
                        .and(recommendedReview.status.eq(status)))
                .groupBy(recommendedReview.review.id, recommendedReview.status)
                .fetchOne();
    }
}
