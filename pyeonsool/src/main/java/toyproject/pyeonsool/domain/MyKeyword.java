package toyproject.pyeonsool.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MyKeyword {

    @Id
    @GeneratedValue
    @Column(name="mykeyword_id")
    private  Long id;

    //멤버
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    //키워드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="keyword_id")
    private Keyword keyword;
}
