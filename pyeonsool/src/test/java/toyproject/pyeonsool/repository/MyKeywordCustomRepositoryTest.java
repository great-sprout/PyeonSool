package toyproject.pyeonsool.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Keyword;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.MyKeyword;
import toyproject.pyeonsool.mykeyword.repository.MyKeywordRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AppConfig.class)
class MyKeywordCustomRepositoryTest {

    @Autowired
    MyKeywordRepository myKeywordRepository;

    @Autowired
    EntityManager em;

    @Test
    void getMemberKeywords(){
        Keyword[] keywords = new Keyword[5];
        String[] keywordNames = {"sweet", "cool", "high", "low", "fresh"};
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        Member member1 = new Member("영준이", "chldudwns121", "1234", "01012341234");
        Member member2 = new Member("춘향이", "cnsgiddl121", "1234", "01055556666");

        em.persist(member);
        em.persist(member1);
        em.persist(member2);
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = new Keyword(keywordNames[i]);
            em.persist(keywords[i]);
        }
        MyKeyword memberKeyword1 = new MyKeyword(member,keywords[1]);
        MyKeyword memberKeyword2 = new MyKeyword(member,keywords[3]);
        MyKeyword memberKeyword3 = new MyKeyword(member,keywords[4]);
        em.persist(memberKeyword1);
        em.persist(memberKeyword2);
        em.persist(memberKeyword3);


        List<String> memberKeywords= myKeywordRepository.myKeywordList(member.getId());

        assertThat(memberKeywords.size()).isEqualTo(3);
    }
}