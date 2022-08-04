package toyproject.pyeonsool.alcoholkeyword.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.DBConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcoholkeyword.domain.AlcoholKeyword;
import toyproject.pyeonsool.keyword.domain.Keyword;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.pyeonsool.alcohol.domain.AlcoholType.BEER;

@DataJpaTest
@Import(DBConfig.class)
class AlcoholKeywordRepositoryTest {

    @Autowired
    AlcoholKeywordRepository alcoholKeywordRepository;

    @Autowired
    EntityManager em;

    @Test
    void should_Success_When_keywordNamesAreObtained() {
        //given
        Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        em.persist(alcohol);

        Keyword[] keywords = new Keyword[3];
        String[] keywordNames = {"sweet", "cool", "high", "low", "fresh"};

        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = new Keyword(keywordNames[i]);
            em.persist(keywords[i]);
        }

        for (int i = 0; i < 3; i++) {
            em.persist(new AlcoholKeyword(keywords[i], alcohol));
        }

        //when
        List<String> alcoholKeywords = alcoholKeywordRepository.getAlcoholKeywords(alcohol.getId());

        //then
        Assertions.assertThat(alcoholKeywords).containsOnly("sweet", "cool", "high");
    }
}