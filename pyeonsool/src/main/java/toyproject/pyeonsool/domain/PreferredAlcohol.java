package toyproject.pyeonsool.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PreferredAlcohol {
    @Id @GeneratedValue
    @Column(name = "preferredAlcohol_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;

}
