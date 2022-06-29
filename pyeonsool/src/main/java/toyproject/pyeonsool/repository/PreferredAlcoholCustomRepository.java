package toyproject.pyeonsool.repository;

import java.util.List;

public interface PreferredAlcoholCustomRepository {
   //내 Like 리스트 반환값
    List<Long> getMyList(Long memberId);
    
    List<Long> getAlcoholIds();

    List<Long> getSojus();
    List<Long> getBeers();
    List<Long> getWines();

    Long getMemberId(Long i);

}
