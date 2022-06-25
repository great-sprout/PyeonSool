package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Review;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long>, ReviewCustomRepository {
}

