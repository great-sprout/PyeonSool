package toyproject.pyeonsool.mykeyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static toyproject.pyeonsool.domain.QKeyword.keyword;
import static toyproject.pyeonsool.domain.QMember.member;
import static toyproject.pyeonsool.domain.QMyKeyword.myKeyword;

@RequiredArgsConstructor
public class MyKeywordCustomRepositoryImpl implements MyKeywordCustomRepository {
    private final JPAQueryFactory queryFactory;

    public List<String> myKeywordList(Long memberId) {
        return queryFactory.select(keyword.name)
                .from(myKeyword)
                .join(keyword).on(myKeyword.keyword.id.eq(keyword.id))
                .join(member).on(myKeyword.member.id.eq(member.id))
                .where(myKeyword.member.id.eq(memberId))
                .fetch();
    }
}
