package toyproject.pyeonsool.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.keyword.domain.Keyword;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findKeywordsByNameIn(List<String> names);
}
