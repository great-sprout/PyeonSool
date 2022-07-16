package toyproject.pyeonsool.review.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.preferredalcohol.repository.PreferredAlcoholRepository;
import toyproject.pyeonsool.review.domain.Review;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
public class ReviewCustomRepositoryImplTest {

    @Autowired
    PreferredAlcoholRepository preferredAlcoholRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    EntityManager em;

    @Test
    void getReviewImage() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        em.persist(member);

        List<Alcohol> alcohols = List.of(
                new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백", 1800,
                        16.5f, null, null, "하이트 진로(주)", "대한민국"),
                new Alcohol(AlcoholType.SOJU, "jamong.jpg", "자몽에 이슬", 1800,
                        16.5f, null, null, "하이트 진로(주)", "대한민국"),
                new Alcohol(AlcoholType.SOJU, "chamisul.jpg", "참이슬", 1800,
                        16.5f, null, null, "하이트 진로(주)", "대한민국"));

        for (Alcohol alcohol : alcohols) {
            em.persist(alcohol);
        }

        em.persist(new Review(member, alcohols.get(0), (byte) 5, "맛있어요"));
        em.persist(new Review(member, alcohols.get(1), (byte) 3, "평범해요"));
        em.persist(new Review(member, alcohols.get(2), (byte) 2, "별로에요"));

        //when
        Page<ReviewImageDto> reviewImage =
                reviewRepository.getReviewImage(member.getId(), PageRequest.of(0, 5));

        //then
        assertThat(reviewImage.getContent().size()).isEqualTo(3);
        assertThat(reviewImage.isFirst()).isTrue();
        assertThat(reviewImage.hasNext()).isFalse();
        assertThat(reviewImage.getContent().size()).isEqualTo(3);


    }
}
