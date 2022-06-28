package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Member;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {

    public Optional<Member> findByUserId(String userId);
    Member findByNickname(String nickname);
}

