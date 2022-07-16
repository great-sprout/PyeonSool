package toyproject.pyeonsool.integration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.pyeonsool.common.exception.api.httpstatus.ForbiddenException;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.recommendedreview.repository.RecommendedReviewRepository;
import toyproject.pyeonsool.review.repository.ReviewRepository;
import toyproject.pyeonsool.review.sevice.ReviewService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@SpringBootTest
@Transactional
class ReviewServiceIntegrationTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    RecommendedReviewRepository recommendedReviewRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EntityManager em;

    // review.getMember().getId()가 null이 아닌 경우는 통합 테스트로 처리
    @Nested
    class EditReviewTest {
        @Test
        void should_Success_When_reviewIsEdited() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) 5, "댓글");
            em.persist(review);

            //when
            //then
            assertThatNoException().isThrownBy(() ->
                    reviewService.editReview(review.getId(), member.getId(), (byte) 3, "수정댓글"));
        }

        @Test
        void should_ThrowException_When_MemberIdAndLoginMemberIdAreNotSame() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) 5, "댓글");
            em.persist(review);

            //when
            //then
            assertThatThrownBy(() -> reviewService.editReview(review.getId(), 10L, (byte) 3, "수정댓글"))
                    .isExactlyInstanceOf(ForbiddenException.class);
        }
    }

    // recommendedReview.getId()가 null이 아닌 경우는 통합 테스트로 처리
    @Nested
    class RecommendReviewTest {
        @Test
        void should_Success_When_RecommendStatusIsDISLIKE() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰", 0L, 1L);
            em.persist(review);

            RecommendedReview recommendedReview = new RecommendedReview(member, review, RecommendStatus.DISLIKE);
            em.persist(recommendedReview);

            //when
            Long recommendReviewId = reviewService.recommendReview(member.getId(), review.getId());

            //then
            assertThat(em.find(RecommendedReview.class, recommendReviewId).getStatus())
                    .isEqualTo(RecommendStatus.LIKE);
            assertThat(review.getRecommendCount()).isEqualTo(1);
            assertThat(review.getNotRecommendCount()).isEqualTo(0);
        }
    }

    // recommendedReview.getId()가 null이 아닌 경우는 통합 테스트로 처리
    @Nested
    class NotRecommendReviewTest {
        @Test
        void should_Success_When_RecommendStatusIsLIKE() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰", 1L, 0L);
            em.persist(review);

            RecommendedReview recommendedReview = new RecommendedReview(member, review, RecommendStatus.LIKE);
            em.persist(recommendedReview);

            //when
            Long recommendReviewId = reviewService.notRecommendReview(member.getId(), review.getId());

            //then
            assertThat(em.find(RecommendedReview.class, recommendReviewId).getStatus())
                    .isEqualTo(RecommendStatus.DISLIKE);
            assertThat(review.getRecommendCount()).isEqualTo(0);
            assertThat(review.getNotRecommendCount()).isEqualTo(1);
        }
    }
}