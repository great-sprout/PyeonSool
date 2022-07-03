package toyproject.pyeonsool.repository;

import java.util.List;

public interface AlcoholKeywordCustomRepository {
    List<String> getAlcoholKeywords(Long AlcoholId);
}
