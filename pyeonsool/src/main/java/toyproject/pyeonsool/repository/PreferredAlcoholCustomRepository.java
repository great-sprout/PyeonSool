package toyproject.pyeonsool.repository;

import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

public interface PreferredAlcoholCustomRepository {
    //내 Like 리스트 반환값
    List<Long> getMyList(Long memberId);

    List<Long> getAlcoholIds();

    List<Long> getAlcoholByType(AlcoholType type);

    Long getLikeCount(Long alcoholId);

    Long getLikeCount(Long alcoholId);

}
