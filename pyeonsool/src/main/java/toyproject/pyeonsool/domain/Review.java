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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private Long recommendCount;
    private Long notRecommendCount;

    public Review(Member member, Alcohol alcohol, Byte grade, String content, Long recommendCount, Long notRecommendCount) {
        this.member = member;
        this.alcohol = alcohol;
        this.grade = grade;
        this.content = content;
        this.recommendCount = recommendCount;
        this.notRecommendCount = notRecommendCount;
    }

    public Review(Member member, Alcohol alcohol, Byte grade, String content) {
        this(member, alcohol, grade, content, 0L, 0L);
    }

    public Review(Long id){
        this.id = id;
    }

    public void plusRecommendCount() { this.recommendCount += 1;
    }

    public void minusRecommendCount() {
        this.recommendCount -= 1;
    }

    public void plusNotRecommendCount() {
        this.notRecommendCount += 1;
    }

    public void minusNotRecommendCount() {
        this.notRecommendCount -= 1;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeGrade(Byte grade){
        this.grade = grade;
    }
}
