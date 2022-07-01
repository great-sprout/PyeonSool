package toyproject.pyeonsool.repository;

import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Review;

import java.util.List;

public interface ReviewCustomRepository {
    List<Byte> getReviewGrades(Long alcoholId);

    List<Review> getReviewImage(Long memberId, Long limit);
}
