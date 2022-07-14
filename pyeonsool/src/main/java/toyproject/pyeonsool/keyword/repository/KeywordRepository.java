package toyproject.pyeonsool.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Keyword;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findKeywordsByNameIn(List<String> names);
}
