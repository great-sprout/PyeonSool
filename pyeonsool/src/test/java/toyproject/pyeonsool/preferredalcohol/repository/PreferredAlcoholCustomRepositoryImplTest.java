package toyproject.pyeonsool.preferredalcohol.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.DBConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.alcoholkeyword.domain.AlcoholKeyword;
import toyproject.pyeonsool.keyword.domain.Keyword;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.mykeyword.domain.MyKeyword;
import toyproject.pyeonsool.preferredalcohol.domain.PreferredAlcohol;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.alcohol.domain.AlcoholType.*;

@DataJpaTest
@Import(DBConfig.class)
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
        Assertions.assertThat(preferredAlcoholRepository.getAlcohols(member.getId(), 12L).size())
                .isEqualTo(3);
    }

    @Test
    @DisplayName("이달의 추천-좋아하는 술")
    void should_When_MonthAlcoholsCalculated() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");

        em.persist(member);
        em.persist(member1);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백",
                1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol1 = new Alcohol(AlcoholType.SOJU, "jamong-chamisul.jpg", "자몽에 이슬",
                1900, 13f, null, null, "하이트 진로(주)", "대한민국");

        em.persist(alcohol);
        em.persist(alcohol1);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member1, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol1));

        //then
        assertThat(preferredAlcoholRepository.count())
                .isEqualTo(3);
        assertThat(preferredAlcoholRepository.getMonthAlcohols().size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("베스트 Like-종류별로 좋아하는 술")
    void should_Success_When_AlcoholByTypeCalculated() { //type, count
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");

        em.persist(member);
        em.persist(member1);

        Alcohol alcohol = new Alcohol(AlcoholType.SOJU, "jinro.jpg", "진로 이즈 백",
                1800, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol1 = new Alcohol(AlcoholType.SOJU, "chamisul.png", "참이슬",
                1950, 16.5f, null, null, "하이트 진로(주)", "대한민국");
        Alcohol alcohol2 = new Alcohol(AlcoholType.BEER, "tiger.jpg", "타이거",
                2500, 5f, null, null, "아시아 퍼시픽 브루어리(주)", "싱가포르");
        Alcohol alcohol3 = new Alcohol(AlcoholType.WINE, "louis-jadot.png", "루이자도 부르고뉴 샤르도네",
                35000, 13f, (byte) 1, (byte) 3, "루이 자도", "프랑스 부르고뉴");

        em.persist(alcohol);
        em.persist(alcohol1);
        em.persist(alcohol2);
        em.persist(alcohol3);

        //when
        em.persist(new PreferredAlcohol(member, alcohol));
        em.persist(new PreferredAlcohol(member1, alcohol));
        em.persist(new PreferredAlcohol(member, alcohol1));
        em.persist(new PreferredAlcohol(member1, alcohol2));
        em.persist(new PreferredAlcohol(member, alcohol3));

        //then
        assertThat(preferredAlcoholRepository.count()).isEqualTo(5);
        assertThat(preferredAlcoholRepository.getAlcoholByType(SOJU, 4).size()).isEqualTo(2);
        assertThat(preferredAlcoholRepository.getAlcoholByType(BEER, 4).size()).isEqualTo(1);
        assertThat(preferredAlcoholRepository.getAlcoholByType(WINE, 4).size()).isEqualTo(1);
    }

    @Test
    void getMemberIds() {
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
    @DisplayName("당신의 편술-회원의 키워드가 포함된 좋아하는 술")
    void should_Success_When_PreferredAlcoholByKeywordCalculated() {
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
        em.persist(alcoholKeyword);
        em.persist(alcoholKeyword1);
        em.persist(alcoholKeyword2);
        AlcoholKeyword alcoholKeyword3 = new AlcoholKeyword(keyword, alcohol1);
        AlcoholKeyword alcoholKeyword4 = new AlcoholKeyword(keyword1, alcohol1);
        AlcoholKeyword alcoholKeyword5 = new AlcoholKeyword(keyword2, alcohol1);
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