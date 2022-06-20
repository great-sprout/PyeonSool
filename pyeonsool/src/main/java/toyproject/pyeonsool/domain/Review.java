package toyproject.pyeonsool.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acohol_id")
    private Alcohol alcohol;

    private Byte grade;

    private String cotent;

    @Enumerated(EnumType.STRING)
    private Enum recommend;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
