package toyproject.pyeonsool.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.repository.RecommendedReviewRepository;
import toyproject.pyeonsool.repository.ReviewRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    RecommendedReviewRepository recommendedReviewRepository;

    @Autowired
    EntityManager em;

    @Test
    void addReview() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        //when
        long reviewId = reviewService.addReview(member.getId(), alcohol.getId(), (byte) 3, "댓글입니다.");

        //then
        assertThat(em.find(Review.class, reviewId)).isNotNull();
    }

    @Nested
    class recommendReviewTest {
        @Test
        void success_existing_entity() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰");
            em.persist(review);

            RecommendedReview recommendedReview = new RecommendedReview(member, review, RecommendStatus.DISLIKE);
            em.persist(recommendedReview);

            //when
            Long recommendReviewId = reviewService.recommendReview(member.getId(), review.getId(), RecommendStatus.LIKE);

            //then
            assertThat(em.find(RecommendedReview.class, recommendReviewId).getStatus())
                    .isEqualTo(RecommendStatus.LIKE);
        }

        @Test
        void success_not_existing_entity() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰");
            em.persist(review);

            //when
            Long recommendReviewId = reviewService.recommendReview(member.getId(), review.getId(), RecommendStatus.LIKE);

            //then
            assertThat(em.find(RecommendedReview.class, recommendReviewId).getStatus())
                    .isEqualTo(RecommendStatus.LIKE);
        }
    }

    @Test
    void cancelRecommendation() {
        //given
        Member member =
                new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        em.persist(alcohol);

        Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰");
        em.persist(review);

        //when
        reviewService.cancelRecommendation(member.getId(), review.getId(), RecommendStatus.LIKE);

        //then
        assertThat(recommendedReviewRepository.findByMemberAndReview(member, review)).isEmpty();
    }

}