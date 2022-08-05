package toyproject.pyeonsool.member.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.DBConfig;
import toyproject.pyeonsool.member.domain.Member;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DBConfig.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void should_Success_When_MemberIsObtained() {
        //given
        String userId = "userId";
        Member member = new Member("nickname", userId, "password", "01012345678");
        em.persist(member);

        //when
        Member findMember = memberRepository.findByUserId(userId).get();

        //then
        assertThat(findMember.getUserId()).isEqualTo(userId);
    }
}