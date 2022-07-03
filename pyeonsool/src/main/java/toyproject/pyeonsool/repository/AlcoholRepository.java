package toyproject.pyeonsool.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;


public interface AlcoholRepository  extends JpaRepository<Alcohol, Long> ,AlcoholCustomRepository{
    Page<Alcohol> findAllByType(AlcoholType alcoholType, Pageable pageable);

}
