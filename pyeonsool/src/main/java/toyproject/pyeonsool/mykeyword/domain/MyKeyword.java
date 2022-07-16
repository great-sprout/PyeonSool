package toyproject.pyeonsool.mykeyword.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.pyeonsool.keyword.domain.Keyword;
import toyproject.pyeonsool.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyKeyword {

    @Id
    @GeneratedValue
    @Column(name = "my_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public MyKeyword(Member member, Keyword keyword) {
        this.member = member;
        this.keyword = keyword;
    }
}
