package toyproject.pyeonsool.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
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
                .orderBy(bySort(condition.getSortType(),condition.getStandard()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(alcohol.count())
                .from(alcohol)
                .where(keywordAlcoholIdIn(condition.getKeywords()),
                        vendorAlcoholIdEq(condition.getVendorName()),
                        alcoholNameContains(condition.getSearch()),
                        alcoholTypeEq(condition.getAlcoholType()))
                .orderBy(bySort(condition.getSortType(),condition.getStandard()));

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
    private OrderSpecifier<?> bySort(String sort, String standard){
        if(sort == null){
            return null;
        }
        else if(sort.equals("abv")){
            if (standard.equals("desc")){
                return alcohol.abv.desc();
            }
            else{
                return alcohol.abv.asc();
            }
        }
       else if(sort.equals("price")){
           if(standard.equals("desc")){
               return alcohol.price.desc();
           }
           else{
               return alcohol.price.asc();
           }
        }
        else return null;
    }



    @Override
    public List<Alcohol> findRelatedAlcohols(Long alcoholId, int limit) {
        return queryFactory.selectFrom(alcohol)
                .where(alcohol.id.in(relatedAlcoholIds(alcoholId)), alcohol.id.ne(alcoholId))
                .groupBy(alcohol.id)
                .limit(limit)
                .fetch();
    }

    private JPQLQuery<Long> relatedAlcoholIds(Long alcoholId) {
        QAlcoholKeyword alcoholKeywordSub = new QAlcoholKeyword("alcoholKeywordSub");

        return JPAExpressions.select(alcoholKeyword.alcohol.id)
                .from(alcoholKeyword)
                .where(alcoholKeyword.keyword.id
                        .in(JPAExpressions.select(alcoholKeywordSub.keyword.id)
                                .from(alcoholKeywordSub)
                                .where(alcoholKeywordSub.alcohol.id.eq(alcoholId))));
    }
}
