package toyproject.pyeonsool.alcoholkeyword.repository;

import java.util.List;

public interface AlcoholKeywordCustomRepository {
    List<String> getAlcoholKeywordNames(Long AlcoholId);
}
