package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Keyword;

import java.util.List;

public interface KeywordRepository  extends JpaRepository<Keyword, Long> {
    List<Keyword> findAllBy();
}
