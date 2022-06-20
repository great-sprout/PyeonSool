package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Keyword;

public interface KeywordRepository  extends JpaRepository<Keyword, Long> {
}
