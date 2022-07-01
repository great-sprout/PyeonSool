package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static toyproject.pyeonsool.domain.QKeyword.keyword;
import static toyproject.pyeonsool.domain.QMember.member;
import static toyproject.pyeonsool.domain.QMyKeyword.myKeyword;

@RequiredArgsConstructor
public class MyKeywordCustomRepositoryImpl implements MyKeywordCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override //나의 키워드 조회
    public List<Long> getMyKeywords(Long loginId) {
        return queryFactory
                .select(keyword.id)
                .from(myKeyword)
                .where(member.id.eq(loginId))
                .fetch();
    }
}
