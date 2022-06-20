package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.PreferredAlcohol;

public interface PreferredAlcoholRepository  extends JpaRepository<PreferredAlcohol, Long> {
}
