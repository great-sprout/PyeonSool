package toyproject.pyeonsool.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.pyeonsool.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AlcoholServiceTest {

    @Autowired
    AlcoholService alcoholService;

    @Autowired
    EntityManager em;

    @Test
    void getAlcoholDetails() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);
        em.persist(new PreferredAlcohol(member, alcohol));

        em.persist(new Review(member, alcohol, (byte) 5, "", Recommend.BASIC));
        em.persist(new Review(member, alcohol, (byte) 3, "", Recommend.BASIC));
        em.persist(new Review(member, alcohol, (byte) 2, "", Recommend.BASIC));

        //when
        AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(alcohol.getId(), member.getId());

        //then
        assertThat(alcoholService.getAlcoholDetails(alcohol.getId(), null))
                .as("로그인 상태가 아닌경우").isNotNull();
        assertThat(alcoholDetails.getGrade()).isEqualTo("3.3");
    }

    @Test
    void likeAlcohol() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        //when
        Long likeAlcoholId = alcoholService.likeAlcohol(alcohol.getId(), member.getId());

        //then
        assertThat(em.find(PreferredAlcohol.class, likeAlcoholId)).isNotNull();
    }

    @Test
    void dislikeAlcohol() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        Long likeAlcoholId = alcoholService.likeAlcohol(alcohol.getId(), member.getId());

        //when
        alcoholService.dislikeAlcohol(alcohol.getId(), member.getId());

        //then
        assertThat(em.find(PreferredAlcohol.class, likeAlcoholId)).isNull();
    }
}