package toyproject.pyeonsool.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.review.repository.ReviewRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@DataJpaTest
@Import(AppConfig.class)
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EntityManager em;

    @Nested
    class getReviewGradesTest {
        @Test
        void should_Success_When_TotalGradeCalculated() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            em.persist(new Review(member, alcohol, (byte) 5, ""));
            em.persist(new Review(member, alcohol, (byte) 3, ""));
            em.persist(new Review(member, alcohol, (byte) 2, ""));

            //when
            List<Byte> reviewRatings = reviewRepository.getReviewGrades(alcohol.getId());
            long gradeSum = reviewRatings.stream()
                    .mapToLong(rating -> rating).sum();

            //then
            assertThat(gradeSum).isEqualTo(10);
            assertThat(reviewRatings.size()).isEqualTo(3);
        }

        @Test
        void should_Success_When_GradeIsNotExist() {
            //given
            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            //when
            List<Byte> reviewRatings = reviewRepository.getReviewGrades(alcohol.getId());
            long sum = reviewRatings.stream()
                    .mapToLong(rating -> rating).sum();

            //then
            assertThat(sum).isEqualTo(0);
            assertThat(reviewRatings.size()).isEqualTo(0);
        }
    }



    @Nested
    class findReviewsByAlcoholTest {
        @Test
        @DisplayName("최신순 리뷰 목록 조회")
        void success_latest_order() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            for (int i = 0; i < 24; i++) {
                em.persist(new Review(member, alcohol, (byte) (i % 5 + 1), "테스트 리뷰 " + i));
            }

            em.persist(new Review(member, alcohol, (byte) 4, "테스트 리뷰", 10L, 0L));

            int SIZE = 10;

            //when
            Page<Review> reviewPage = reviewRepository.findReviewsByAlcohol(alcohol,
                    PageRequest.of(0, SIZE, Sort.by(Sort.Direction.DESC, "id")));
            List<Review> latestOrderReviews = reviewPage.getContent();

            //then
            assertThat(latestOrderReviews).isSortedAccordingTo((o1, o2) -> o2.getId().compareTo(o1.getId()));
            assertThat(latestOrderReviews.get(0).getRecommendCount()).isEqualTo(10);
            assertThat(latestOrderReviews.get(0).getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewPage.isFirst()).isTrue();
            assertThat(reviewPage.hasNext()).isTrue();
        }

        @Test
        @DisplayName("추천순 리뷰 목록 조회")
        void success_recommended_order() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            for (int i = 0; i < 24; i++) {
                em.persist(new Review(member, alcohol, (byte) (i % 5 + 1), "테스트 리뷰 " + i,
                        (long) i, (long) (24 - i)));
            }

            int SIZE = 10;

            //when
            Page<Review> reviewPage = reviewRepository.findReviewsByAlcohol(alcohol,
                    PageRequest.of(0, SIZE, Sort.by(Sort.Direction.DESC, "recommendCount")));
            List<Review> recommendedOrderReviews = reviewPage.getContent();

            //then
            assertThat(recommendedOrderReviews).isSortedAccordingTo((o1, o2) ->
                    (int) ((o2.getRecommendCount() - o2.getNotRecommendCount()) -
                            (o1.getRecommendCount() - o1.getNotRecommendCount())));
            assertThat(reviewPage.isFirst()).isTrue();
            assertThat(reviewPage.hasNext()).isTrue();
        }
    }
}