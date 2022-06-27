package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.domain.Review;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@DataJpaTest
@Import(AppConfig.class)
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EntityManager em;

    @Test
    void getReviewRatings() {
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
        long sum = reviewRatings.stream()
                .mapToLong(rating -> rating).sum();

        //then
        assertThat(sum).isEqualTo(10);
        assertThat(reviewRatings.size()).isEqualTo(3);
    }

    @Nested
    class findReviewsByAlcoholTest {
        @Test
        void success_latest_order() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            for (int i = 0; i < 25; i++) {
                em.persist(new Review(member, alcohol, (byte)(i % 5 + 1), "테스트 리뷰 " + i));
            }

            int SIZE = 10;

            //when
            Page<Review> reviewPage = reviewRepository.findReviewsByAlcohol(alcohol,
                    PageRequest.of(0, SIZE, Sort.by(Sort.Direction.ASC, "id")));
            List<Review> latestOrderReviews = reviewPage.getContent();

            //then
            assertThat(latestOrderReviews).isSortedAccordingTo((o1, o2) -> o1.getId().compareTo(o2.getId()));
            assertThat(reviewPage.isFirst()).isTrue();
            assertThat(reviewPage.hasNext()).isTrue();
        }

        //TODO 리뷰 추천, 비추천 개수 조회 구현 후 테스트 코드 작성
        @Test
        void success_recommended_order() {
            //given
            Member member =
                    new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            em.persist(member);

            Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀");
            em.persist(alcohol);

            for (int i = 0; i < 5; i++) {
                Review review = new Review(member, alcohol, (byte) (i % 5 + 1), "테스트 리뷰 " + i);
                em.persist(review);

            }

            int SIZE = 10;

            //when
            List<Review> latestOrderReviews = reviewRepository.findReviewsByAlcohol(alcohol,
                    PageRequest.of(0, SIZE, Sort.by(Sort.Direction.ASC, "id"))).getContent();

            //then
            assertThat(latestOrderReviews).isSortedAccordingTo((o1, o2) -> o1.getId().compareTo(o2.getId()));
        }
    }
}