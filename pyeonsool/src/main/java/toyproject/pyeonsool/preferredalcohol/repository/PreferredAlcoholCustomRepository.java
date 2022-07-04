package toyproject.pyeonsool.preferredalcohol.repository;

import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

public interface PreferredAlcoholCustomRepository {
    List<Alcohol> getMonthAlcohols();

    List<Long> getAlcoholByType(AlcoholType type,int count);

    Long getLikeCount(Long alcoholId);

    //내 Like 리스트 반환값
    List<Alcohol> getAlcohols(Long memberId, Long limit);

    //나의 키워드가 포함된 선호하는 알콜 조회
    List<Alcohol> getPreferredAlcoholByKeyword(Long loginMember);
}