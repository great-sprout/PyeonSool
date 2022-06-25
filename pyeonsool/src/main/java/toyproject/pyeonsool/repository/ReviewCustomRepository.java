package toyproject.pyeonsool.repository;

import java.util.List;

public interface ReviewCustomRepository {
    List<Byte> getReviewGrades(Long alcoholId);
}
