package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class PreferredAlcoholRepositoryTest {

    @Autowired
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Autowired
    EntityManager em;

    @Test
    void existsByMemberAndAlcohol() {
        //given
        Member member = new Member(null, null, null, null);
        em.persist(member);

        Alcohol alcohol = new Alcohol(null, null, null, null, null, null,
                null, null, null);
        em.persist(alcohol);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));

        //then
        Assertions.assertThat(preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol))
                .isTrue();
    }

    @Test
    void removeByMemberAndAlcohol() {
        //given
        Member member = new Member(null, null, null, null);
        em.persist(member);

        Alcohol alcohol = new Alcohol(null, null, null, null, null, null,
                null, null, null);
        em.persist(alcohol);

        PreferredAlcohol preferredAlcohol = new PreferredAlcohol(member, alcohol);
        em.persist(preferredAlcohol);

        //when
        preferredAlcoholRepository.removeByMemberAndAlcohol(member, alcohol);

        //then
        Assertions.assertThat(preferredAlcoholRepository.findById(preferredAlcohol.getId())).isEmpty();
    }
}