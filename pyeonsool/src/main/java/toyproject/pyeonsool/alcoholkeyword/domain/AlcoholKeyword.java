package toyproject.pyeonsool.alcoholkeyword.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.keyword.domain.Keyword;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlcoholKeyword {

    @Id
    @GeneratedValue
    @Column(name = "alcohol_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;

    public AlcoholKeyword(Keyword keyword, Alcohol alcohol) {
        this.keyword = keyword;
        this.alcohol = alcohol;
    }
}
