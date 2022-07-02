package toyproject.pyeonsool.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.repository.AlcoholSearchConditionDto;
import toyproject.pyeonsool.repository.PreferredAlcoholRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.domain.AlcoholType.*;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;
import static toyproject.pyeonsool.domain.VendorName.GS25;

@SpringBootTest
@Transactional
class AlcoholServiceTest {

    @Autowired
    AlcoholService alcoholService;
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Autowired
    EntityManager em;

    @Test
    void getAlcoholDetails() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);
        em.persist(new PreferredAlcohol(member, alcohol));

        em.persist(new Review(member, alcohol, (byte) 5, ""));
        em.persist(new Review(member, alcohol, (byte) 3, ""));
        em.persist(new Review(member, alcohol, (byte) 2, ""));

        Keyword[] keywords = new Keyword[3];
        String[] keywordNames = {"sweet", "cool", "heavy"};

        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = new Keyword(keywordNames[i]);
            em.persist(keywords[i]);
            em.persist(new AlcoholKeyword(keywords[i], alcohol));
        }

        //when
        //AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(alcohol.getId(), member.getId());

        //then
       /* assertThat(alcoholService.getAlcoholDetails(alcohol.getId(), null))
                .as("로그인 상태가 아닌경우").isNotNull();
        assertThat(alcoholDetails.getGrade()).isEqualTo("3.3");
        assertThat(alcoholDetails.getKeywords()).containsOnly("달콤함", "청량함", "무거움");
   */
    }

    @Test
    void likeAlcohol() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        //when
        Long likeAlcoholId = alcoholService.likeAlcohol(alcohol.getId(), member.getId());

        //then
        assertThat(em.find(PreferredAlcohol.class, likeAlcoholId)).isNotNull();
    }

    @Test
    void dislikeAlcohol() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국", 0L);
        em.persist(alcohol);

        Long likeAlcoholId = alcoholService.likeAlcohol(alcohol.getId(), member.getId());

        //when
        alcoholService.dislikeAlcohol(alcohol.getId(), member.getId());

        //then
        assertThat(em.find(PreferredAlcohol.class, likeAlcoholId)).isNull();
    }

    @Test
    void getAlcoholImages() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        Alcohol alcohol2 = new Alcohol(AlcoholType.SOJU, "test.jpg", "옐로우테일2", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        Alcohol alcohol3 = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일3", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);
        em.persist(alcohol2);
        em.persist(alcohol3);

        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol2));
        em.persist(new PreferredAlcohol(member, alcohol3));

        //when
        List<AlcoholImageDto> likeList = alcoholService.getAlcoholImages(member.getId());

        //then
        assertThat(likeList.size()).isEqualTo(3);
    }

    @Test
    void typeAlcohol() {
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");
        Member member2 = new Member("춘향이", "cnsgiddl121", "1234", "01055556666");

        em.persist(member);
        em.persist(member1);
        em.persist(member2);

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
        Alcohol alcohol11 = new Alcohol(BEER, "san-miguel.png", "산", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol12 = new Alcohol(BEER, "san-miguel.png", "산미", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol13 = new Alcohol(BEER, "san-miguel.png", "산미구", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol14 = new Alcohol(BEER, "san-miguel.png", "산미구엘", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol15 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페", 3000,
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

        em.persist(alcohol11);
        em.persist(alcohol12);
        em.persist(alcohol13);
        em.persist(alcohol14);
        em.persist(alcohol15);

        em.persist(new PreferredAlcohol(member, alcohol1));
        em.persist(new PreferredAlcohol(member1, alcohol1));
        em.persist(new PreferredAlcohol(member2, alcohol1));
        em.persist(new PreferredAlcohol(member, alcohol2));
        em.persist(new PreferredAlcohol(member2, alcohol2));
        em.persist(new PreferredAlcohol(member, alcohol3));
        //when

        int SIZE = 8;
        //when
        Page<AlcoholDto> alcoholType = alcoholService.findAlcoholPage(
                PageRequest.of(0, SIZE, Sort.by(Sort.Direction.ASC, "id")),
                new AlcoholSearchConditionDto(BEER, List.of("cool", "clear"), "리", GS25, null));

        //then
        assertThat(alcoholType.isLast()).isTrue();
    }
}