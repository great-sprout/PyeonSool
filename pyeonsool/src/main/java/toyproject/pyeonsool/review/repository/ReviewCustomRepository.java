package toyproject.pyeonsool.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewCustomRepository {
    List<Byte> getReviewGrades(Long alcoholId);

    Page<ReviewImageDto> getReviewImage(Long memberId, Pageable pageable);
}
