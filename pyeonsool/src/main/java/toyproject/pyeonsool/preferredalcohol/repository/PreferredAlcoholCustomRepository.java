package toyproject.pyeonsool.preferredalcohol.repository;

import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

public interface PreferredAlcoholCustomRepository {
    List<Alcohol> getMonthAlcohols();

    List<Long> getAlcoholByType(AlcoholType type,int count);

    Long getLikeCount(Long alcoholId);

    List<Alcohol> getAlcohols(Long memberId, Long limit);

    List<Alcohol> getPreferredAlcoholByKeyword(Long loginMember);
}
