package toyproject.pyeonsool.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.pyeonsool.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AlcoholServiceTest {

    @Autowired
    AlcoholService alcoholService;

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
        AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(alcohol.getId(), member.getId());

        //then
        assertThat(alcoholService.getAlcoholDetails(alcohol.getId(), null))
                .as("로그인 상태가 아닌경우").isNotNull();
        assertThat(alcoholDetails.getGrade()).isEqualTo("3.3");
        assertThat(alcoholDetails.getKeywords()).containsOnly("달콤함", "청량함", "무거움");
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
                (byte) 3, (byte) 2, "우리집", "대한민국");
        em.persist(alcohol);

        Long likeAlcoholId = alcoholService.likeAlcohol(alcohol.getId(), member.getId());

        //when
        alcoholService.dislikeAlcohol(alcohol.getId(), member.getId());

        //then
        assertThat(em.find(PreferredAlcohol.class, likeAlcoholId)).isNull();
    }

    @Test
    void findLikeList() {
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
        List<MyPageDto> likeList = alcoholService.findLikeList(member);

        //then
        assertThat(likeList.size()).isEqualTo(3);
    }
}