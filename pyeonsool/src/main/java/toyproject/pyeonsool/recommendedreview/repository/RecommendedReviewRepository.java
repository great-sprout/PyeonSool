package toyproject.pyeonsool.recommendedreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.recommendedreview.domain.RecommendStatus;
import toyproject.pyeonsool.recommendedreview.domain.RecommendedReview;
import toyproject.pyeonsool.review.domain.Review;

import java.util.Optional;

public interface RecommendedReviewRepository extends JpaRepository<RecommendedReview, Long> {
    Optional<RecommendedReview> findByMemberAndReview(Member member, Review review);

    Optional<RecommendedReview> findByMemberAndReviewAndStatus(Member member, Review review, RecommendStatus status);
}
