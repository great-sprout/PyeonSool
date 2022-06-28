package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class PreferredAlcoholCustomRepositoryImplTest {

    @Autowired
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Autowired
    EntityManager em;


    @Test
    void getMyList() {
        //given
        Member member = new Member("준영이","chlwnsdud121","1234","01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU,"jinro.jpg","진로 이즈 백",1800,16.5f,null,null,"하이트 진로(주)","대한민국");
        em.persist(alcohol);

        Alcohol alcoholOne = new Alcohol(AlcoholType.SOJU,"jinro.jpg","자몽에 이슬",1800,16.5f,null,null,"하이트 진로(주)","대한민국");
        em.persist(alcoholOne);

        Alcohol alcoholTwo = new Alcohol(AlcoholType.SOJU,"jinro.jpg","참이슬",1800,16.5f,null,null,"하이트 진로(주)","대한민국");
        em.persist(alcoholTwo);
        //when
        em.persist(new PreferredAlcohol(member,alcohol));
        em.persist(new PreferredAlcohol(member,alcoholOne));
        em.persist(new PreferredAlcohol(member,alcoholTwo));

        //then
        Assertions.assertThat(preferredAlcoholRepository.getMyList(member.getId()).size())
                .isEqualTo(3);

    }
}