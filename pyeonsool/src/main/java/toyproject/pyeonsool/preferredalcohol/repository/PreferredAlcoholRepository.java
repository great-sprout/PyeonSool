package toyproject.pyeonsool.preferredalcohol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

public interface PreferredAlcoholRepository  extends JpaRepository<PreferredAlcohol, Long>, PreferredAlcoholCustomRepository {
    boolean existsByMemberAndAlcohol(Member member, Alcohol alcohol);

    void removeByMemberAndAlcohol(Member member, Alcohol alcohol);

    Long countByMemberId(Long memberId);
}