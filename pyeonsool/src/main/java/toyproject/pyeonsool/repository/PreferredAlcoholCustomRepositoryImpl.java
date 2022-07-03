package toyproject.pyeonsool.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.QAlcoholKeyword;
import toyproject.pyeonsool.domain.QMyKeyword;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
import static toyproject.pyeonsool.domain.QAlcoholKeyword.*;
import static toyproject.pyeonsool.domain.QMyKeyword.*;
import static toyproject.pyeonsool.domain.QPreferredAlcohol.preferredAlcohol;

@RequiredArgsConstructor
public class PreferredAlcoholCustomRepositoryImpl implements PreferredAlcoholCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override //모두가 좋아하는 술(Alcohol)
    public List<Alcohol> getMonthAlcohols() {
        return queryFactory
                .select(preferredAlcohol.alcohol)
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

    //select pa.alcohol_id from preferred_alcohol pa left join alcohol_keyword ak
    //on pa.alcohol_id = ak.alcohol_id
    //where pa.alcohol_id in (select alcohol_id from alcohol_keyword
    //where keyword_id in (select keyword_id from my_keyword where member_id=1))
    //group by pa.alcohol_id
    //limit 12;
    @Override //나의 키워드가 포함된 알콜과 일치하는 선호하는 알콜 조회
    public List<Alcohol> getPreferredAlcoholByKeyword(Long loginMember) {
        return queryFactory
                .select(preferredAlcohol.alcohol)
                .from(preferredAlcohol, alcoholKeyword) //세타 조인
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
