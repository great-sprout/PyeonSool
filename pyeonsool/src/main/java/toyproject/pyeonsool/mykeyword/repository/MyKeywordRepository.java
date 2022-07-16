package toyproject.pyeonsool.mykeyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.MyKeyword;

public interface MyKeywordRepository  extends JpaRepository<MyKeyword, Long>,MyKeywordCustomRepository {
    void deleteByMember(Member member);
}
