package toyproject.pyeonsool.preferredalcohol.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.DBConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.preferredalcohol.domain.PreferredAlcohol;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DBConfig.class)
class PreferredAlcoholRepositoryTest {

    @Autowired
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Autowired
    EntityManager em;

    @Test
    void should_Exist_When_PreferredAlcoholIsObtained() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        em.persist(new PreferredAlcohol(member, alcohol));

        //when
        //then
        assertThat(preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol))
                .isTrue();
    }

    @Test
    void should_Success_When_CountingPreferredAlcohols() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        em.persist(new PreferredAlcohol(member, alcohol));

        //when
        //then
        assertThat(preferredAlcoholRepository.countByMemberId(member.getId())).isEqualTo(1);
    }


    @Test
    void should_Success_When_PreferredAlcoholIsRemoved() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        PreferredAlcohol preferredAlcohol = new PreferredAlcohol(member, alcohol);
        em.persist(preferredAlcohol);

        //when
        preferredAlcoholRepository.removeByMemberAndAlcohol(member, alcohol);

        //then
        assertThat(preferredAlcoholRepository.findById(preferredAlcohol.getId())).isEmpty();
    }
}