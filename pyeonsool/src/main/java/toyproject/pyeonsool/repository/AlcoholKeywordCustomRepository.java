package toyproject.pyeonsool.repository;

import toyproject.pyeonsool.domain.AlcoholKeyword;

import java.util.List;

public interface AlcoholKeywordCustomRepository {
    List<String> getAlcoholKeywords(Long AlcoholId);
}
