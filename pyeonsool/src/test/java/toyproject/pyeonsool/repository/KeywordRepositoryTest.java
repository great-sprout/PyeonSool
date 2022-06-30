package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Keyword;

import javax.persistence.EntityManager;

import java.util.Optional;

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
    void findByName() {
        //given
        Keyword[] keywords = new Keyword[3];
        String[] keywordNames = {"sweet", "cool", "high", "low", "fresh"};

        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = new Keyword(keywordNames[i]);
            em.persist(keywords[i]);
        }

        //when
        //then
        assertThat(keywordRepository.findByName("sweet")).isNotEmpty();
        assertThat(keywordRepository.findByName("test")).isEmpty();

    }
}