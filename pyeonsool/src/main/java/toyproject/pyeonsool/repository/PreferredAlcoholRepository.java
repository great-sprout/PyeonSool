package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

import java.util.List;

public interface PreferredAlcoholRepository  extends JpaRepository<PreferredAlcohol, Long>,PreferredAlcoholCustomRepository {

    boolean existsByMemberAndAlcohol(Member member, Alcohol alcohol);

    void removeByMemberAndAlcohol(Member member, Alcohol alcohol);

    //마이페이지 에서 내 Like 리스트 전체 조회
    List<PreferredAlcohol> findAllPreferredAlcoholsByMember(Member member);




}
