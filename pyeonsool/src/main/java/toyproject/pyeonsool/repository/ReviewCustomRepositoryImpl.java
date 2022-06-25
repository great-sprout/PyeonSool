package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.QReview;

import java.util.List;

import static toyproject.pyeonsool.domain.QReview.*;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Byte> getReviewRatings(Long alcoholId) {
        return queryFactory.select(review.grade)
                .from(review)
                .where(review.alcohol.id.eq(alcoholId))
                .fetch();
    }
}
