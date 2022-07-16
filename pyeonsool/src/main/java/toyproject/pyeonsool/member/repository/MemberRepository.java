package toyproject.pyeonsool.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsByUserId(String userId);

    boolean existsByPhoneNumber(String phoneNumber);
}
