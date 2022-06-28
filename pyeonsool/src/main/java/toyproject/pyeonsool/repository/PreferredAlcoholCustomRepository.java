package toyproject.pyeonsool.repository;

import java.util.List;

public interface PreferredAlcoholCustomRepository {
   //내 Like 리스트 반환값
    List<Long> getMyList(Long memberId);
    
    List<Long> getAlcoholIds();
}
