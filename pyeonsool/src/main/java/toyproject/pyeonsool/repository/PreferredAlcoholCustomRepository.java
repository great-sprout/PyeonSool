package toyproject.pyeonsool.repository;

import java.util.List;

public interface PreferredAlcoholCustomRepository {

    List<Long> getAlcoholIds();
    List<Long> getSojus();
    List<Long> getBeers();
    List<Long> getWines();
}
