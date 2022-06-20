package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Alcohol;

public interface AlcoholRepository  extends JpaRepository<Alcohol, Long> {
}
