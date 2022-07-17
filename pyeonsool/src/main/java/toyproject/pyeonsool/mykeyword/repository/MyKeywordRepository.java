package toyproject.pyeonsool.mykeyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.mykeyword.domain.MyKeyword;

public interface MyKeywordRepository  extends JpaRepository<MyKeyword, Long>,MyKeywordCustomRepository {
    void deleteByMember(Member member);
}
