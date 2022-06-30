package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.QAlcohol;
import toyproject.pyeonsool.domain.QPreferredAlcohol;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.domain.QPreferredAlcohol.preferredAlcohol;

@RequiredArgsConstructor
public class PreferredAlcoholCustomRepositoryImpl implements PreferredAlcoholCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Long> getAlcoholIds() { //모두가 좋아하는 술(alcohol_id)
        return queryFactory
                .select(preferredAlcohol.alcohol.id)
                .from(preferredAlcohol)
                .groupBy(preferredAlcohol.alcohol.id)
                .orderBy(preferredAlcohol.id.count().desc())
                .limit(12)
                .fetch();
    }

    @Override
    public List<Long> getAlcoholByType(AlcoholType type) { //타입별 좋아하는 술(alcohol_id)
        return queryFactory
                .select(preferredAlcohol.alcohol.id)
                .from(preferredAlcohol)
                .join(preferredAlcohol.alcohol, alcohol)
                .where(alcohol.type.eq(type))
                .groupBy(preferredAlcohol.alcohol.id)
                .orderBy(preferredAlcohol.alcohol.id.count().desc())
                .limit(6)
                .fetch();
    }

    public Long getMemberId(Long alcoholId){
        return queryFactory
                .select(preferredAlcohol.member.id.count())
                .from(preferredAlcohol)
                .where(preferredAlcohol.alcohol.id.eq(alcoholId))
                .fetchOne();
    }

    @Override
    public Long getLikeCount(Long alcoholId) {

        return queryFactory
                .select(preferredAlcohol.member.id.count())
                .from(preferredAlcohol)
                .where(preferredAlcohol.alcohol.id.eq(alcoholId))
                .fetchOne();
    }

    //마이페이지 내Like 리스트 쿼리dsl문
    @Override
    public List<Alcohol> getAlcohols(Long memberId, Long limit) {
        return queryFactory.select(alcohol)
                .from(preferredAlcohol)
                .join(preferredAlcohol.alcohol, alcohol)
                .where(preferredAlcohol.member.id.eq(memberId))
                .limit(limit)
                .fetch();
    }

}
