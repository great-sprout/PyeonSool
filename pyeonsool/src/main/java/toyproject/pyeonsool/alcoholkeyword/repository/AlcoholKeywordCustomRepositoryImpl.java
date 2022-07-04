package toyproject.pyeonsool.alcoholkeyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcoholKeyword.alcoholKeyword;
import static toyproject.pyeonsool.domain.QKeyword.keyword;

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
