package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.domain.AlcoholKeyword;
import toyproject.pyeonsool.domain.QAlcoholKeyword;
import toyproject.pyeonsool.domain.QKeyword;
import toyproject.pyeonsool.domain.QMember;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcoholKeyword.*;
import static toyproject.pyeonsool.domain.QKeyword.*;
import static toyproject.pyeonsool.domain.QMember.*;

@RequiredArgsConstructor
public class AlcoholKeywordCustomRepositoryImpl implements AlcoholKeywordCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> getAlcoholKeywords(Long alcoholId) {
        return queryFactory.select(keyword.name)
                .from(alcoholKeyword)
                .join(alcoholKeyword.keyword, keyword)
                .where(alcoholKeyword.alcohol.id.eq(alcoholId))
                .fetch();
    }
}
