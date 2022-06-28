package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import toyproject.pyeonsool.domain.QAlcohol;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcohol.*;

import java.util.List;


import static toyproject.pyeonsool.domain.QPreferredAlcohol.*;

@RequiredArgsConstructor
public class PreferredAlcoholCustomRepositoryImpl implements PreferredAlcoholCustomRepository {

    private final JPAQueryFactory queryFactory;


    //마이페이지 내Like 리스트 쿼리dsl문
    @Override
    public List<Long> getMyList(Long memberId){
        return queryFactory.select(alcohol.id)
                .from(preferredAlcohol)
                .join(preferredAlcohol.alcohol, alcohol)
                .where(preferredAlcohol.member.id.eq(memberId))
                .limit(12)
                .fetch();
      }
    //좋아하는 술 아이디
    @Override
    public List<Long> getAlcoholIds() {
        return queryFactory
                .select(preferredAlcohol.alcohol.id)
                .from(preferredAlcohol)
                .groupBy(preferredAlcohol.alcohol.id)
                .orderBy(preferredAlcohol.id.count().desc())
                .limit(12)
                .fetch();
    }
}
