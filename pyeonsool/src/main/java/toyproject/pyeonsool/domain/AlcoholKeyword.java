package toyproject.pyeonsool.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AlcoholKeyword {

    @Id
    @GeneratedValue
    @Column(name="alcoholkeyword_id")
    private Long id;


    //키워드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="keyword_id")
    private Keyword keyword;

    //알콜
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="alcohol_id")
    private Alcohol alcohol;
}
