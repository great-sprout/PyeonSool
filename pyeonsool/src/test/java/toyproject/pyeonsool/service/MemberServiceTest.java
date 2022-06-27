package toyproject.pyeonsool.service;

import org.assertj.core.api.Assertions;
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
        List<MyPageDto> likeList = memberService.findLikeList(member);

        //then
        assertThat(likeList.size()).isEqualTo(3);
    }


}