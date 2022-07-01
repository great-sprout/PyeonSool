package toyproject.pyeonsool.repository;

import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

public interface PreferredAlcoholCustomRepository {
    //내 Like 리스트 반환값
    List<Long> getMyList(Long memberId);

    List<Long> getAlcoholIds();

    List<Long> getAlcoholByType(AlcoholType type,int count);

    Long getLikeCount(Long alcoholId);

    //나의 키워드가 포함된 선호하는 알콜 조회
    List<Long> getPreferredAlcoholByKeyword(List<Long> keywordAlcohol);

}
