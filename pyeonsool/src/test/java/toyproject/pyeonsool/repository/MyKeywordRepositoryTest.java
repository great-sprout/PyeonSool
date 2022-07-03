package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Keyword;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.MyKeyword;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class MyKeywordRepositoryTest {

    @Autowired
    MyKeywordRepository myKeywordRepository;

    @Autowired
    EntityManager em;

    @Test
    void deleteByMemberId() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        em.persist(member);

        List<Keyword> keywords = List.of(
                new Keyword("cool"),
                new Keyword("fresh"),
                new Keyword("middle")
        );
        for (Keyword keyword : keywords) {
            em.persist(keyword);
            em.persist(new MyKeyword(member, keyword));
        }
        em.flush();
        em.clear();

        //when
        myKeywordRepository.deleteByMemberId(member.getId());

        //then
        Assertions.assertThat(myKeywordRepository.findAll()).isEmpty();
    }
}