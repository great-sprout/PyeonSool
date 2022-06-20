package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Member;

public interface MemberRepository  extends JpaRepository<Member, Long> {
}
