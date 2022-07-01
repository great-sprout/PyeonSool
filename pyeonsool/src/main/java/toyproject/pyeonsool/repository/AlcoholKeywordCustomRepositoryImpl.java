package toyproject.pyeonsool.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static toyproject.pyeonsool.domain.QAlcohol.alcohol;
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
    @Override //나의 키워드와 일치하는 알콜 조회
    public List<Long> getAlcoholByKeyword(List<Long> myKeyword) {
        return queryFactory
                .select(alcohol.id)
                .from(alcoholKeyword)
                .where(alcoholKeyword.keyword.id.in(myKeyword.get(0), myKeyword.get(1), myKeyword.get(2)))
                .fetch();
    }

}
