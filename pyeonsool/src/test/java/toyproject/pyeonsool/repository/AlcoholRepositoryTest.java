package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholKeyword;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.Keyword;
import toyproject.pyeonsool.service.AlcoholTypeDto;

import javax.persistence.EntityManager;

import java.util.List;

import static toyproject.pyeonsool.domain.AlcoholType.*;

@DataJpaTest
@Import(AppConfig.class)
class AlcoholRepositoryTest {
    @Autowired
    AlcoholRepository alcoholRepository;

    @Autowired
    EntityManager em;

    @Test
    void getAlcoholType() {
        //given
        Alcohol alcohol1 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠a", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol2 = new Alcohol(WINE, "san-miguel.png", "산미구엘 페일필젠s", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol3 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠f", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol4 = new Alcohol(SOJU, "san-miguel.png", "산미구엘 페일필젠g", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol5 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠h", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");

        em.persist(alcohol1);
        em.persist(alcohol2);
        em.persist(alcohol3);
        em.persist(alcohol4);
        em.persist(alcohol5);

        //when
        List<Alcohol> alcoholType = alcoholRepository.findAllByType(BEER);


        //then
        for(Alcohol a : alcoholType) {
            System.out.println("alcoholType = " + a.getName());
        }


    }
}