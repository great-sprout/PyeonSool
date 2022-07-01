package toyproject.pyeonsool.repository;

import java.util.List;

public interface AlcoholKeywordCustomRepository {
    List<String> getAlcoholKeywords(Long AlcoholId);

    //키워드와 일치하는 알콜 조회
    List<Long> getAlcoholByKeyword(List<Long> myKeyword);
}
