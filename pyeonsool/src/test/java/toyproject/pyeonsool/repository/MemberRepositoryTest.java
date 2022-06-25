package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.Member;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AppConfig.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void findByUserId() {
        //given
        String userId = "userId";
        Member member = new Member("nickname", userId, "password", "01012345678");
        em.persist(member);

        //when
        Member findMember = memberRepository.findByUserId(userId).get();

        //then
        Assertions.assertThat(findMember.getUserId()).isEqualTo(userId);
    }
}