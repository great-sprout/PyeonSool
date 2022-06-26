package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

public interface AlcoholRepository  extends JpaRepository<Alcohol, Long> {



    List<Alcohol> findAllByType(AlcoholType alcoholType);


}
