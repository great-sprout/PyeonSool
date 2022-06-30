package toyproject.pyeonsool.repository;

import toyproject.pyeonsool.domain.Keyword;

import java.util.List;

public interface MyKeywordCustomRepository {
    List<String> myKeywordList(Long memberId);
}
