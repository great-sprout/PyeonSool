package toyproject.pyeonsool.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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
    ReviewRepository reviewRepository;

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

    //수정 서비스 테스트
    @Test
    void editReview() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        Review review = new Review(member, alcohol, (byte)5, "댓글");
        em.persist(review);

        em.flush();
        em.clear();

        //when
        reviewService.editReview(review.getId(), (byte)3, "수정댓글");
        em.flush();
        em.clear();

        //then
        assertThat(em.find(Review.class, review.getId()).getContent()).isEqualTo("수정댓글");
        assertThat(em.find(Review.class, review.getId()).getGrade()).isEqualTo((byte)3);
    }
    @Nested
    class getReviewPageTest {
        @Test
        void success_not_recommend() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰", 0L, 1L);
            em.persist(review);

            em.persist(new RecommendedReview(member, review, RecommendStatus.DISLIKE));
            //when
            ReviewDto reviewDto = reviewService
                    .getReviewPage(PageRequest.of(0, 1), alcohol.getId(), member.getId())
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.DISLIKE);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(1);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(0);
        }

        @Test
        void success_recommend() {
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
            ReviewDto reviewDto = reviewService
                    .getReviewPage(PageRequest.of(0, 1), alcohol.getId(), member.getId())
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.LIKE);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(1);
        }

        @Test
        void success_not_exist_recommend() {
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
            ReviewDto reviewDto = reviewService
                    .getReviewPage(PageRequest.of(0, 1), alcohol.getId(), member.getId())
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isNull();
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(0);
        }
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

            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰", 0L, 1L);
            em.persist(review);

            RecommendedReview recommendedReview = new RecommendedReview(member, review, RecommendStatus.DISLIKE);
            em.persist(recommendedReview);

            //when
            Long recommendReviewId = reviewService.recommendReview(member.getId(), review.getId(), RecommendStatus.LIKE);

            //then
            assertThat(em.find(RecommendedReview.class, recommendReviewId).getStatus())
                    .isEqualTo(RecommendStatus.LIKE);
            assertThat(review.getRecommendCount()).isEqualTo(1);
            assertThat(review.getNotRecommendCount()).isEqualTo(0);
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
            assertThat(review.getRecommendCount()).isEqualTo(1);
            assertThat(review.getNotRecommendCount()).isEqualTo(0);
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

        Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰", 1L, 0L);
        em.persist(review);

        //when
        reviewService.cancelRecommendation(member.getId(), review.getId(), RecommendStatus.LIKE);

        //then
        assertThat(recommendedReviewRepository.findByMemberAndReview(member, review)).isEmpty();
        assertThat(review.getRecommendCount()).isEqualTo(1);
        assertThat(review.getNotRecommendCount()).isEqualTo(0);
    }

}