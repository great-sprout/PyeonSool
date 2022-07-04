package toyproject.pyeonsool.alcoholkeyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.AlcoholKeyword;

public interface AlcoholKeywordRepository extends JpaRepository<AlcoholKeyword, Long>, AlcoholKeywordCustomRepository {
}
