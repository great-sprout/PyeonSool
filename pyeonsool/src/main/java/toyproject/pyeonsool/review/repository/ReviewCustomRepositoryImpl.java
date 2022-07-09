package toyproject.pyeonsool.review.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Review;


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

    @Override
    public Page<Review> findReviewsByAlcohol(Alcohol alcohol, Pageable pageable) {
        JPAQuery<Review> query = queryFactory.selectFrom(review)
                .where(review.alcohol.eq(alcohol))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(review.alcohol.eq(alcohol));

        return PageableExecutionUtils.getPage(
                setReviewOrder(query, pageable.getSort()).fetch(), pageable, countQuery::fetchOne);
    }

    private JPAQuery<Review> setReviewOrder(JPAQuery<Review> query, Sort sort) {
        for (Sort.Order o : sort) {
            if (o.getProperty().equals("recommendCount")) {
                if (o.isAscending()) {
                    query.orderBy(review.recommendCount.subtract(review.notRecommendCount).asc());
                } else {
                    query.orderBy(review.recommendCount.subtract(review.notRecommendCount).desc());
                }
            } else {
                PathBuilder pathBuilder = new PathBuilder(review.getType(), review.getMetadata());
                query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                        pathBuilder.get(o.getProperty())));
            }
        }

        return query;
    }
}
