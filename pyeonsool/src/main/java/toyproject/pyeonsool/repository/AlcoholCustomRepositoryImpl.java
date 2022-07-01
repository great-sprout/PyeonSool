package toyproject.pyeonsool.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.service.AlcoholImageDto;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.domain.QAlcoholKeyword.alcoholKeyword;
import static toyproject.pyeonsool.domain.QKeyword.keyword;
import static toyproject.pyeonsool.domain.QMyKeyword.*;
import static toyproject.pyeonsool.domain.QPreferredAlcohol.*;
import static toyproject.pyeonsool.domain.QVendor.vendor;

@RequiredArgsConstructor
public class AlcoholCustomRepositoryImpl implements AlcoholCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Alcohol> findAllByType(Pageable pageable, AlcoholSearchConditionDto condition) {

        List<Alcohol> result = queryFactory.selectFrom(alcohol)
                .where(keywordAlcoholIdIn(condition.getKeywords()),
                        vendorAlcoholIdEq(condition.getVendorName()),
                        alcoholNameContains(condition.getSearch()),
                        alcoholTypeEq(condition.getAlcoholType()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(alcohol.count())
                .from(alcohol)
                .where(keywordAlcoholIdIn(condition.getKeywords()),
                        vendorAlcoholIdEq(condition.getVendorName()),
                        alcoholNameContains(condition.getSearch()),
                        alcoholTypeEq(condition.getAlcoholType()));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression alcoholTypeEq(AlcoholType alcoholType) {
        if (alcoholType == null) {
            return null;
        }
        return alcohol.type.eq(alcoholType);
    }

    private BooleanExpression vendorAlcoholIdEq(VendorName vendorName) {
        if (vendorName == null) {
            return null;
        }

        return alcohol.id.in(JPAExpressions
                .select(vendor.alcohol.id)
                .from(vendor)
                .where(vendor.name.eq(vendorName)));
    }

    private BooleanExpression alcoholNameContains(String search) {
        if (!hasText(search)) {
            return null;
        }
        return alcohol.name.contains(search);
    }

    private BooleanExpression keywordAlcoholIdIn(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return null;
        }

        return alcohol.id.in(queryFactory
                .select(alcoholKeyword.alcohol.id)
                .from(alcoholKeyword)
                .join(alcoholKeyword.keyword, keyword)
                .where(alcoholKeyword.keyword.name.in(keywords)));
    }

    @Override
    public List<AlcoholImageDto> alcoholImagesByMemberId(Long memberId) {
        //서브쿼리문 별칭!!!!!!!!!
        List<Tuple> list_count = queryFactory
                .select(preferredAlcohol.alcohol.id,
                        preferredAlcohol.alcohol.id.count().as("count"))
                .from(preferredAlcohol)
                .groupBy(preferredAlcohol.alcohol.id)
                .fetch();
        return queryFactory
                .select(alcohol.id, alcohol.fileName)
                .from(alcohol)
                .leftJoin(ExpressionUtils.as(JPAExpressions
                        .select(preferredAlcohol.alcohol.id, preferredAlcohol.alcohol.id.count().as("count"))
                        .from(preferredAlcohol)
                        .groupBy(preferredAlcohol.alcohol.id), "like_count"))
                .on(like_count.alcohol.id.eq(alcohol.id))
                .where(alcohol.id.in(JPAExpressions
                        .select(ak.id)
                        .from(myKeyword.as("mk"))
                        .join(alcoholKeyword.as("ak"))
                        .on(ak.keyword.id.eq(mk.keyword.id))
                        .where()))
                .fetch();



    }
}
