package toyproject.pyeonsool.review.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.review.domain.Review;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.alcohol.domain.AlcoholType.BEER;

@DataJpaTest
@Import(AppConfig.class)
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EntityManager em;

    @Nested
    class GetReviewGradesTest {
        @Test
        void should_Success_When_TotalGradeCalculated() {
            //given
            Member member = createMember();
            em.persist(member);

            Alcohol alcohol = createAlcohol();
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
            Alcohol alcohol = createAlcohol();
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

    private Member createMember() {
        return new Member("?????????", "chlwnsdud121", "1234", "01012345678");
    }


    private Alcohol createAlcohol() {
        return new Alcohol(BEER, "san-miguel.png", "???????????? ????????????", 3000,
                5f, null, null, "???????????? ????????????", "?????????");
    }

    @Nested
    class FindReviewsByAlcoholTest {
        @Test
        void should_Success_When_ReviewsAreObtainedInTheLatestOrder() {
            //given
            Member member = createMember();
            em.persist(member);

            Alcohol alcohol = createAlcohol();
            em.persist(alcohol);

            for (int i = 0; i < 24; i++) {
                em.persist(new Review(member, alcohol, (byte) (i % 5 + 1), "????????? ?????? " + i));
            }

            em.persist(new Review(member, alcohol, (byte) 4, "????????? ??????", 10L, 0L));

            int SIZE = 10;

            //when
            Page<Review> reviewPage = reviewRepository.findReviewsByAlcohol(alcohol,
                    PageRequest.of(0, SIZE, Sort.by(Sort.Direction.DESC, "id")));
            List<Review> latestOrderReviews = reviewPage.getContent();

            //then
            assertThat(latestOrderReviews).isSortedAccordingTo((o1, o2) -> o2.getId().compareTo(o1.getId()));
            assertThat(latestOrderReviews.get(0).getRecommendCount()).isEqualTo(10);
            assertThat(latestOrderReviews.get(0).getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewPage.getTotalElements()).isEqualTo(25);
            assertThat(reviewPage.isFirst()).isTrue();
            assertThat(reviewPage.hasNext()).isTrue();
        }

        @Test
        void should_Success_When_ReviewsAreObtainedInTheRecommendedOrder() {
            //given
            Member member = createMember();
            em.persist(member);

            Alcohol alcohol = createAlcohol();
            em.persist(alcohol);

            for (int i = 0; i < 24; i++) {
                em.persist(new Review(member, alcohol, (byte) (i % 5 + 1), "????????? ?????? " + i,
                        (long) i, (long) (24 - i)));
            }

            int SIZE = 10;

            //when
            Page<Review> reviewPage = reviewRepository.findReviewsByAlcohol(alcohol,
                    PageRequest.of(0, SIZE, Sort.by(Sort.Direction.DESC, "recommendCount")));
            List<Review> recommendedOrderReviews = reviewPage.getContent();

            //then
            assertThat(recommendedOrderReviews).describedAs("???????????? - ??????????????? ????????? ??????")
                    .isSortedAccordingTo((o1, o2) -> (int) ((o2.getRecommendCount() - o2.getNotRecommendCount()) -
                            (o1.getRecommendCount() - o1.getNotRecommendCount())));
            assertThat(reviewPage.isFirst()).isTrue();
            assertThat(reviewPage.hasNext()).isTrue();
        }
    }
}