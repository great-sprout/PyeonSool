package toyproject.pyeonsool.alcohol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.pyeonsool.alcohol.domain.Alcohol;

import java.util.List;

public interface AlcoholCustomRepository {
    Page<Alcohol> findAllByType(Pageable pageable, AlcoholSearchConditionDto condition);

    List<Alcohol> findRelatedAlcohols(Long alcoholId, int limit);
}
