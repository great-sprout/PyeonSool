package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        Alcohol alcohol1 = new Alcohol(BEER, "san-miguel.png", "산", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol2 = new Alcohol(BEER, "san-miguel.png", "산미", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol3 = new Alcohol(BEER, "san-miguel.png", "산미구", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol4 = new Alcohol(BEER, "san-miguel.png", "산미구엘", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol5 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol6 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol7 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol8 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol9 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠1", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol10 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠12", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");

        em.persist(alcohol1);
        em.persist(alcohol2);
        em.persist(alcohol3);
        em.persist(alcohol4);
        em.persist(alcohol5);
        em.persist(alcohol6);
        em.persist(alcohol7);
        em.persist(alcohol8);
        em.persist(alcohol9);
        em.persist(alcohol10);
        int SIZE = 8;
        //when
        List<Alcohol> alcoholType = alcoholRepository.findAllByType(BEER,PageRequest.of(0, SIZE, Sort.by(Sort.Direction.ASC, "id")));


        //then
        for(Alcohol a : alcoholType) {
            System.out.println("alcoholType = " + a.getName());
        }
    }
}