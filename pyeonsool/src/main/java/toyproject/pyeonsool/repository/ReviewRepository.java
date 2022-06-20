package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Review;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
}

