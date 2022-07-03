package toyproject.pyeonsool.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Review;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long>, ReviewCustomRepository {
    Page<Review> findReviewsByAlcohol(Alcohol alcohol, Pageable pageable);

}

