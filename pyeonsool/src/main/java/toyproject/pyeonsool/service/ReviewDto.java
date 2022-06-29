package toyproject.pyeonsool.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.domain.Review;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long writerId;
    private String nickname;
    private Byte grade;
    private String content;
    private LocalDateTime lastModifiedDate;
    private Long recommendCount;
    private Long notRecommendCount;
    private RecommendStatus myRecommendStatus;

    public static ReviewDto of(Review review, RecommendStatus status) {
        return new ReviewDto(review.getId(),
                review.getMember().getId(),
                review.getMember().getNickname(),
                review.getGrade(),
                review.getContent(),
                review.getLastModifiedDate(),
                review.getRecommendCount(),
                review.getNotRecommendCount(),
                status);
    }
}
