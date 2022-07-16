package toyproject.pyeonsool.member.sevice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.exception.form.IncorrectPasswordException;
import toyproject.pyeonsool.common.exception.form.IncorrectUserIdException;
import toyproject.pyeonsool.keyword.domain.Keyword;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.mykeyword.domain.MyKeyword;
import toyproject.pyeonsool.mykeyword.repository.MyKeywordRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MyKeywordRepository myKeywordRepository;
    @Autowired
    EntityManager em;

    // loginMember.getId()가 null이면 예외가 발생하므로 통합 테스트 진행
    @Nested
    class FindLoginMemberTest {
        @Test
        void should_Success_When_LoginMemberIsObtained() {
            //given
            String userId = "userId";
            String password = "password";
            Member member = new Member("nickname", userId, password, "01012345678");

            em.persist(member);
            //when
            LoginMember loginMember = memberService.findLoginMember(userId, password);

            //then
            assertThat(loginMember.getId()).isEqualTo(member.getId());
            assertThat(loginMember.getNickname()).isEqualTo(member.getNickname());
        }

        @Test
        void should_ThrowException_When_IncorrectUserId() {
            //given
            //when
            //then
            assertThatThrownBy(() -> memberService.findLoginMember("userId", "password"))
                    .isExactlyInstanceOf(IncorrectUserIdException.class);
        }

        @Test
        void should_ThrowException_When_IncorrectPassword() {
            //given
            String userId = "userId";
            String password = "password";
            Member member = new Member("nickname", userId, password, "01012345678");

            em.persist(member);
            //when
            //then
            assertThatThrownBy(() -> memberService.findLoginMember("userId", "wrongPassword"))
                    .isExactlyInstanceOf(IncorrectPasswordException.class);
        }
    }

    @Test
    void kroMyKeyword() {
        Keyword[] keywords = new Keyword[5];
        String[] keywordNames = {"sweet", "cool", "strong", "middle", "fresh"};
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
        MyKeyword memberKeyword1 = new MyKeyword(member, keywords[1]);
        MyKeyword memberKeyword2 = new MyKeyword(member, keywords[3]);
        MyKeyword memberKeyword3 = new MyKeyword(member, keywords[4]);
        em.persist(memberKeyword1);
        em.persist(memberKeyword2);
        em.persist(memberKeyword3);


        List<String> memberKeywords1 = memberService.getMyKeywordsKOR(member.getId());
        assertThat(memberKeywords1.get(1)).isEqualTo("도수 중간");
    }

    @Test
    void editMemberKeywords() {
        //given
        Member member = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
        em.persist(member);

        List<Keyword> keywords = List.of(
                new Keyword("cool"),
                new Keyword("fresh"),
                new Keyword("middle"),
                new Keyword("high"),
                new Keyword("low")
        );

        for (Keyword keyword : keywords) {
            em.persist(keyword);
        }

        for (int i = 0; i < 3; i++) {
            em.persist(new MyKeyword(member, keywords.get(i)));

        }
        em.flush();
        em.clear();

        //when
        memberService.editMemberKeywords(List.of("middle", "high", "low"), member.getId());

        //then
        assertThat(myKeywordRepository.findAll())
                .extracting("keyword").extracting("name")
                .contains("middle", "high", "low");
    }


}