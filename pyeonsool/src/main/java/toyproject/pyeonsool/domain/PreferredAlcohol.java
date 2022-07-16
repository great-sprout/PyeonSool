package toyproject.pyeonsool.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferredAlcohol {
    @Id
    @GeneratedValue
    @Column(name = "preferred_alcohol_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;

    public PreferredAlcohol(Member member, Alcohol alcohol) {
        this.member = member;
        this.alcohol = alcohol;
    }
}
