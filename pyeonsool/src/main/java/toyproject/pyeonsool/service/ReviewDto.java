package toyproject.pyeonsool.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.Review;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long writerId;
    private String nickname;
    private Byte grade;
    private String content;
    private LocalDateTime lastModifiedDate;
    private int likeCount;
    private int dislikeCount;

    public static ReviewDto from(Review review) {
        return new ReviewDto(review.getId(),
                review.getContent(),
                review.getGrade(),
                review.getContent(),
                review.getLastModifiedDate(),
                0,
                0);
    }
}
