package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
    public Page<ReviewImageDto> getReviewImage(Long memberId, Pageable pageable) {
        List<ReviewImageDto> result = queryFactory
                .select(new QReviewImageDto(
                        review.id,
                        alcohol.id,
                        alcohol.type,
                        alcohol.fileName,
                        review.lastModifiedDate,
                        review.grade,
                        review.content,
                        review.recommendCount,
                        review.notRecommendCount))
                .from(review)
                .join(review.alcohol, alcohol)
                .where(review.member.id.eq(memberId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(review.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(review.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
}
