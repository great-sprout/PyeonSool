package toyproject.pyeonsool.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.*;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class PreferredAlcoholCustomRepositoryImplTest {

    @Autowired
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Autowired
    EntityManager em;

    @Test
    void getAlcoholIds() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");
        Member member2 = new Member("춘향이", "cnsgiddl121", "1234", "01055556666");

        em.persist(member);
        em.persist(member1);
        em.persist(member2);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백",
                1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol1 = new Alcohol(AlcoholType.SOJU, "jamong-chamisul.jpg", "자몽에 이슬",
                1900, 13f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol2 = new Alcohol(AlcoholType.SOJU, "chamisul.png", "참이슬",
                1950, 16.5f, null, null, "하이트 진로(주)", "대한민국");

        em.persist(alcohol);
        em.persist(alcohol1);
        em.persist(alcohol2);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member1, alcohol));
        em.persist(new PreferredAlcohol(member2, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol1));
        em.persist(new PreferredAlcohol(member2, alcohol1));
        em.persist(new PreferredAlcohol(member, alcohol2));

        //then
        assertThat(preferredAlcoholRepository.getAlcoholIds().size())
                .isEqualTo(3);
    }
}