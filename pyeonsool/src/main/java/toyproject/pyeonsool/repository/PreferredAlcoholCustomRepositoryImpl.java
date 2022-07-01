package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.domain.QPreferredAlcohol.preferredAlcohol;

@RequiredArgsConstructor
public class PreferredAlcoholCustomRepositoryImpl implements PreferredAlcoholCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override //모두가 좋아하는 술(alcohol_id)
    public List<Long> getAlcoholIds() {
        return queryFactory
                .select(preferredAlcohol.alcohol.id)
                .from(preferredAlcohol)
                .groupBy(preferredAlcohol.alcohol.id)
                .orderBy(preferredAlcohol.id.count().desc())
                .limit(12)
                .fetch();
    }

    @Override //타입별 좋아하는 술(alcohol_id)
    public List<Long> getAlcoholByType(AlcoholType type,int count) {
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

    @Override //나의 키워드가 포함된 알콜과 일치하는 선호하는 알콜 조회
    public List<Long> getPreferredAlcoholByKeyword(List<Long> keywordAlcoholId) {
        return queryFactory
                .select(preferredAlcohol.alcohol.id)
                .from(preferredAlcohol)
                .where(preferredAlcohol.alcohol.id.in(keywordAlcoholId))
                .fetch();
    }
}
