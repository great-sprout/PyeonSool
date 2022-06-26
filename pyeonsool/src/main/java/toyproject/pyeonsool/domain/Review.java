package toyproject.pyeonsool.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;

    private Byte grade;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private Recommend recommend;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Review(Member member, Alcohol alcohol, Byte grade, String content, Recommend recommend) {
        this.member = member;
        this.alcohol = alcohol;
        this.grade = grade;
        this.content = content;
        this.recommend = recommend;
    }

    public Review(Member member, Alcohol alcohol, Byte grade, String content) {
        this(member, alcohol, grade, content, Recommend.BASIC);
    }
}
