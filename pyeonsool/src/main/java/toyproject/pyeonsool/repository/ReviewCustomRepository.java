package toyproject.pyeonsool.repository;

import java.util.List;

public interface ReviewCustomRepository {
    List<Byte> getReviewRatings(Long alcoholId);
}
