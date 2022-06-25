package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.Recommend;
import toyproject.pyeonsool.domain.Review;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        em.persist(new Review(member, alcohol, (byte) 5, "", Recommend.BASIC));
        em.persist(new Review(member, alcohol, (byte) 3, "", Recommend.BASIC));
        em.persist(new Review(member, alcohol, (byte) 2, "", Recommend.BASIC));

        //when
        List<Byte> reviewRatings = reviewRepository.getReviewRatings(alcohol.getId());
        long sum = reviewRatings.stream()
                .mapToLong(rating -> rating).sum();

        //then
        Assertions.assertThat(sum).isEqualTo(10);
        Assertions.assertThat(reviewRatings.size()).isEqualTo(3);
    }
}