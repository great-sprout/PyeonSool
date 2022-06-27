package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.RecommendedReview;
import toyproject.pyeonsool.domain.Review;

import java.util.Optional;

public interface RecommendedReviewRepository extends JpaRepository<RecommendedReview, Long> {
    Optional<RecommendedReview> findByMemberAndReview(Member member, Review review);
}