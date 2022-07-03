package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.*;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.domain.AlcoholType.SOJU;



@DataJpaTest
@Import(AppConfig.class)
class PreferredAlcoholCustomRepositoryImplTest {

    @Autowired
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Autowired
    EntityManager em;


    @Test
    void getAlcohols() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        em.persist(member);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백", 1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        em.persist(alcohol);

        Alcohol alcoholOne = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "자몽에 이슬", 1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        em.persist(alcoholOne);

        Alcohol alcoholTwo = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "참이슬", 1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        em.persist(alcoholTwo);
        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member, alcoholOne));
        em.persist(new PreferredAlcohol(member, alcoholTwo));

        //then
        Assertions.assertThat(preferredAlcoholRepository.getAlcohols(member.getId(),12L).size())
                .isEqualTo(3);
    }

    @Test
    void getMonthAlcohols() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");
        Member member2 = new Member("춘향이", "cnsgiddl121", "1234", "01055556666");

        em.persist(member);
        em.persist(member1);
        em.persist(member2);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백",
                1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol1 = new Alcohol(AlcoholType.SOJU, "jamong-chamisul.jpg", "자몽에 이슬",
                1900, 13f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol2 = new Alcohol(AlcoholType.SOJU, "chamisul.png", "참이슬",
                1950, 16.5f, null, null, "하이트 진로(주)", "대한민국");

        em.persist(alcohol);
        em.persist(alcohol1);
        em.persist(alcohol2);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member1, alcohol));
        em.persist(new PreferredAlcohol(member2, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol1));
        em.persist(new PreferredAlcohol(member2, alcohol1));
        em.persist(new PreferredAlcohol(member, alcohol2));

        //then
        assertThat(preferredAlcoholRepository.getMonthAlcohols().size())
                .isEqualTo(3);
    }
    @Test
    void getMemberIds(){
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");
        Member member2 = new Member("춘향이", "cnsgiddl121", "1234", "01055556666");

        em.persist(member);
        em.persist(member1);
        em.persist(member2);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백",
                1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol1 = new Alcohol(AlcoholType.SOJU, "jamong-chamisul.jpg", "자몽에 이슬",
                1900, 13f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol2 = new Alcohol(AlcoholType.SOJU, "chamisul.png", "참이슬",
                1950, 16.5f, null, null, "하이트 진로(주)", "대한민국");

        em.persist(alcohol);
        em.persist(alcohol1);
        em.persist(alcohol2);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member1, alcohol));
        em.persist(new PreferredAlcohol(member2, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol1));
        em.persist(new PreferredAlcohol(member2, alcohol1));
        em.persist(new PreferredAlcohol(member, alcohol2));
        
        assertThat(preferredAlcoholRepository.getLikeCount(alcohol.getId())).isEqualTo(3);
        assertThat(preferredAlcoholRepository.getLikeCount(alcohol1.getId())).isEqualTo(2);
        assertThat(preferredAlcoholRepository.getLikeCount(alcohol2.getId())).isEqualTo(1);
    }

    @Test
    void getPreferredAlcoholByKeyword() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        em.persist(member);

        Keyword keyword = new Keyword("sweet");
        Keyword keyword1 = new Keyword("clear");
        Keyword keyword2 = new Keyword("cool");
        em.persist(keyword);
        em.persist(keyword1);
        em.persist(keyword2);

        MyKeyword myKeyword = new MyKeyword(member, keyword);
        MyKeyword myKeyword1 = new MyKeyword(member, keyword1);
        MyKeyword myKeyword2 = new MyKeyword(member, keyword2);
        em.persist(myKeyword);
        em.persist(myKeyword1);
        em.persist(myKeyword2);

        Alcohol alcohol = new Alcohol(SOJU, "jinro.jpg", "진로 이즈 백", 1800, 16.5f,
                null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol1 = new Alcohol(SOJU, "chamisul.png", "참이슬", 1950, 16.5f,
                null, null, "하이트 진로(주)", "대한민국");
        em.persist(alcohol);
        em.persist(alcohol1);

        AlcoholKeyword alcoholKeyword = new AlcoholKeyword(keyword, alcohol);
        AlcoholKeyword alcoholKeyword1 = new AlcoholKeyword(keyword1, alcohol);
        AlcoholKeyword alcoholKeyword2 = new AlcoholKeyword(keyword2, alcohol);
        AlcoholKeyword alcoholKeyword3 = new AlcoholKeyword(keyword, alcohol1);
        AlcoholKeyword alcoholKeyword4 = new AlcoholKeyword(keyword1, alcohol1);
        AlcoholKeyword alcoholKeyword5 = new AlcoholKeyword(keyword2, alcohol1);
        em.persist(alcoholKeyword);
        em.persist(alcoholKeyword1);
        em.persist(alcoholKeyword2);
        em.persist(alcoholKeyword3);
        em.persist(alcoholKeyword4);
        em.persist(alcoholKeyword5);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol1));

        //then
        assertThat(preferredAlcoholRepository.getPreferredAlcoholByKeyword(member.getId()).size()).isEqualTo(2);
    }
}