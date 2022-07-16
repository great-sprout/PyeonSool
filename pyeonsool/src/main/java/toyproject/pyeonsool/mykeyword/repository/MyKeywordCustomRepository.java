package toyproject.pyeonsool.mykeyword.repository;

import java.util.List;

public interface MyKeywordCustomRepository {
    List<String> myKeywordList(Long memberId);
}
