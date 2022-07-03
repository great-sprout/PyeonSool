package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Keyword;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    EntityManager em;

    @Test
    void findKeywordsBy() {
        //given
        Keyword keyword = new Keyword("sweet");
        Keyword keyword2 = new Keyword("clear");
        Keyword keyword1 = new Keyword("cool");

        //when
        em.persist(keyword);
        em.persist(keyword1);
        em.persist(keyword2);

        //then
        assertThat(keywordRepository.findAllBy().size()).isEqualTo(3);
    }
}