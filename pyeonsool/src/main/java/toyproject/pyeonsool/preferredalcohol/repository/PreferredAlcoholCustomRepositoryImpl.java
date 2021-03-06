package toyproject.pyeonsool.preferredalcohol.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;

import java.util.List;

import static toyproject.pyeonsool.alcohol.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.alcoholkeyword.domain.QAlcoholKeyword.alcoholKeyword;
import static toyproject.pyeonsool.mykeyword.domain.QMyKeyword.myKeyword;
import static toyproject.pyeonsool.preferredalcohol.domain.QPreferredAlcohol.preferredAlcohol;

@RequiredArgsConstructor
public class PreferredAlcoholCustomRepositoryImpl implements PreferredAlcoholCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Alcohol> getMonthAlcohols() {
        return queryFactory
                .select(preferredAlcohol.alcohol)
                .from(preferredAlcohol)
                .groupBy(preferredAlcohol.alcohol.id)
                .orderBy(preferredAlcohol.id.count().desc())
                .limit(12)
                .fetch();
    }

    @Override
    public List<Long> getAlcoholByType(AlcoholType type, int count) {
        return queryFactory
                .select(preferredAlcohol.alcohol.id)
                .from(preferredAlcohol)
                .join(preferredAlcohol.alcohol, alcohol)
                .where(alcohol.type.eq(type))
                .groupBy(preferredAlcohol.alcohol.id)
                .orderBy(preferredAlcohol.alcohol.id.count().desc())
                .limit(count)
                .fetch();
    }

    @Override
    public Long getLikeCount(Long alcoholId) {
        return queryFactory
                .select(preferredAlcohol.member.id.count())
                .from(preferredAlcohol)
                .where(preferredAlcohol.alcohol.id.eq(alcoholId))
                .fetchOne();
    }

    @Override
    public List<Alcohol> getAlcohols(Long memberId, Long limit) {
        return queryFactory.select(alcohol)
                .from(preferredAlcohol)
                .join(preferredAlcohol.alcohol, alcohol)
                .where(preferredAlcohol.member.id.eq(memberId))
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Alcohol> getPreferredAlcoholByKeyword(Long loginMember) {
        return queryFactory
                .select(preferredAlcohol.alcohol)
                .from(preferredAlcohol, alcoholKeyword)
                .where(preferredAlcohol.alcohol.id.in(
                        JPAExpressions
                                .select(alcoholKeyword.alcohol.id)
                                .from(alcoholKeyword)
                                .where(alcoholKeyword.keyword.id.in(
                                        JPAExpressions
                                                .select(myKeyword.keyword.id)
                                                .from(myKeyword)
                                                .where(myKeyword.member.id.eq(loginMember))
                                ))
                ))
                .groupBy(preferredAlcohol.alcohol.id)
                .limit(12)
                .fetch();
    }
}
