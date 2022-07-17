package toyproject.pyeonsool.recommendedreview.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.pyeonsool.review.domain.Review;
import toyproject.pyeonsool.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedReview {

    @Id
    @GeneratedValue
    @Column(name = "recommended_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;

    public RecommendedReview(Member member, Review review, RecommendStatus status) {
        this.member = member;
        this.review = review;
        this.status = status;
    }

    public void changeStatus(RecommendStatus status) {
        this.status = status;
    }
}
