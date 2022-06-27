package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.*;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@DataJpaTest
@Import(AppConfig.class)
class RecommendedReviewRepositoryTest {

    @Autowired
    RecommendedReviewRepository recommendedReviewRepository;

    @Autowired
    EntityManager em;

    @Test
    void findByMemberAndReview() {
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
        RecommendedReview recommendedReview = new RecommendedReview(member, review, RecommendStatus.LIKE);
        em.persist(recommendedReview);

        //then
        assertThat(em.find(RecommendedReview.class, recommendedReview.getId())).isNotNull();
    }
}