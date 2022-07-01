package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Review;
import toyproject.pyeonsool.service.QReviewImageDto;
import toyproject.pyeonsool.service.ReviewImageDto;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.domain.QReview.review;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Byte> getReviewGrades(Long alcoholId) {
        return queryFactory.select(review.grade)
                .from(review)
                .where(review.alcohol.id.eq(alcoholId))
                .fetch();
    }

    @Override
    public List<Review> getReviewImage(Long memberId, Long limit){
            List<ReviewImageDto> result = queryFactory
                .select(new QReviewImageDto(
                        review.reviewId,
                        image,
                        review.lastModifiedDate,
                        review.grade,
                        review.content,
                        review.recommendCount,
                        review.notRecommendCount))
                .from(review)
                .join(review.alcohol,alcohol)
                .where(review.member.id.eq(memberId))
                .limit(limit)
                .fetch();

            return result;
    }
}
