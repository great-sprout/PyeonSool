package toyproject.pyeonsool.recommendedreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.DBConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.recommendedreview.domain.RecommendStatus;
import toyproject.pyeonsool.recommendedreview.domain.RecommendedReview;
import toyproject.pyeonsool.review.domain.Review;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.alcohol.domain.AlcoholType.BEER;

@DataJpaTest
@Import(DBConfig.class)
class RecommendedReviewRepositoryTest {

    @Autowired
    RecommendedReviewRepository recommendedReviewRepository;

    @Autowired
    EntityManager em;

    @Test
    void should_Success_When_RecommendedReviewIsObtained_ByMemberAndReview() {
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
        assertThat(recommendedReviewRepository.findByMemberAndReview(member, review)).isNotEmpty();
    }

    @Test
    void should_Success_When_RecommendedReviewIsObtained_ByMemberAndReviewAndStatus() {
        //given
        Member member =
                new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member2 =
                new Member("영준이", "chldudwns121", "1234", "01023456789");
        em.persist(member);
        em.persist(member2);

        Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        em.persist(alcohol);

        Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰");
        em.persist(review);

        //when
        RecommendedReview recommendedReview = new RecommendedReview(member, review, RecommendStatus.LIKE);
        RecommendedReview notRecommendedReview = new RecommendedReview(member2, review, RecommendStatus.DISLIKE);
        em.persist(recommendedReview);
        em.persist(notRecommendedReview);

        //then
        assertThat(recommendedReviewRepository.findByMemberAndReviewAndStatus(member, review, RecommendStatus.LIKE))
                .isNotEmpty();
        assertThat(recommendedReviewRepository.findByMemberAndReviewAndStatus(member, review, RecommendStatus.DISLIKE))
                .isEmpty();
        assertThat(recommendedReviewRepository.findByMemberAndReviewAndStatus(member2, review, RecommendStatus.DISLIKE))
                .isNotEmpty();
        assertThat(recommendedReviewRepository.findByMemberAndReviewAndStatus(member2, review, RecommendStatus.LIKE))
                .isEmpty();
    }
}