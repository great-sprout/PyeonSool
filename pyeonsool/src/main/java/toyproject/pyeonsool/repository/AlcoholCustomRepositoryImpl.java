package toyproject.pyeonsool.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import toyproject.pyeonsool.domain.*;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.domain.QAlcoholKeyword.alcoholKeyword;
import static toyproject.pyeonsool.domain.QKeyword.keyword;
import static toyproject.pyeonsool.domain.QVendor.vendor;

@RequiredArgsConstructor
public class AlcoholCustomRepositoryImpl implements AlcoholCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Alcohol> findAllByType(AlcoholType alcoholType, Pageable pageable,
                                       List<String> keywords, String search, VendorName vendorName) {
       /* List<Alcohol> result = queryFactory.selectFrom(alcohol)
                .where(keywordAlcoholIdIn(keywords),
                        alcoholNameLike(search),
                        vendorAlcoholIdEq(vendorName),
                        alcoholTypeEq(alcoholType))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();*/
        List<Alcohol> result = queryFactory.selectFrom(alcohol)
                .where(
                        vendorAlcoholIdEq(vendorName),

                        alcoholTypeEq(alcoholType))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(alcohol.count())
                .where(

                        vendorAlcoholIdEq(vendorName),
                        alcoholTypeEq(alcoholType)
                );
        System.out.println("keywordAlcoholIdIn(keywords) = " + keywordAlcoholIdIn(keywords));
        System.out.println("alcoholNameLike(search) = " + alcoholNameLike(search));
        System.out.println("vendorAlcoholIdEq(vendorName) = " + vendorAlcoholIdEq(vendorName));
        System.out.println("alcoholTypeEq(alcoholType) = " + alcoholTypeEq(alcoholType));
        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression alcoholTypeEq(AlcoholType alcoholType) {
        return alcoholType != null ? alcohol.type.eq(alcoholType) : null;
    }

    private BooleanExpression vendorAlcoholIdEq(VendorName vendorName) {
        return vendorName != null ? alcohol.id.in(JPAExpressions
                .select(vendor.alcohol.id)
                .from(vendor)
                .where(vendor.name.eq(vendorName))) : null;
    }

    private BooleanExpression alcoholNameLike(String search) {

        return hasText(search) ? alcohol.name.contains(search) :null;
    }

    private BooleanExpression keywordAlcoholIdIn(List<String> keywords) {
        return keywords != null ? alcohol.id.in(queryFactory
                .select(alcoholKeyword.alcohol.id)
                .from(alcoholKeyword)
                .join(alcoholKeyword.keyword, keyword)
                .where(alcoholKeyword.keyword.name.in(keywords))) : null;
    }
}
