package toyproject.pyeonsool.repository;

import java.util.List;

public interface MyKeywordCustomRepository {

    //나의 키워드 조회
    List<Long> getMyKeywords(Long loginId);

}
