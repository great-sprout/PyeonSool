package toyproject.pyeonsool.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Nested
    class findLoginMemberTest {
        @Test
        void success() {
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
        void fail_id() {
            //given
            //when
            //then
            assertThrowsExactly(IllegalArgumentException.class,
                    () -> memberService.findLoginMember("userId", "password"),
                    "잘못된 아이디입니다.");
        }

        @Test
        void fail_password() {
            //given
            String userId = "userId";
            String password = "password";
            Member member = new Member("nickname", userId, password, "01012345678");

            //when
            //then
            assertThrowsExactly(IllegalArgumentException.class,
                    () -> memberService.findLoginMember("userId", "wrongPassword"),
                    "잘못된 비밀번호입니다.");
        }
    }

    @Nested
    class signupTest {
        @BeforeEach
        void init() {
            em.persist(new Member("nickname", "userId", "password", "01012345678"));
            em.flush();
            em.clear();
        }

        @Test
        void fail_by_nickname() {
            //given
            //when
            //then
            assertThrowsExactly(IllegalArgumentException.class, () -> memberService.signup("nickname",
                            "userId2", "password", "01012345678", null),
                    "이미 사용중인 닉네임입니다.");
        }

        @Test
        void fail_by_userId() {
            //given
            //when
            //then
            assertThrowsExactly(IllegalArgumentException.class, () -> memberService.signup("nickname2",
                            "userId", "password", "01011111111", null),
                    "이미 사용중인 아이디입니다.");
        }

        @Test
        void fail_by_phoneNumber() {
            //given
            //when
            //then
            assertThrowsExactly(IllegalArgumentException.class, () -> memberService.signup("nickname2",
                            "userId2", "password2", "01012345678", null),
                    "이미 사용중인 핸드폰 번호입니다.");
        }
    }
}