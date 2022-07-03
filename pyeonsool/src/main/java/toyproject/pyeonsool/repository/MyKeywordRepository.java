package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.MyKeyword;

import java.util.List;

public interface MyKeywordRepository  extends JpaRepository<MyKeyword, Long>,MyKeywordCustomRepository {
    List<Long> findByMember(LoginMember loginMember);
    void deleteByMember(Member member);
}
