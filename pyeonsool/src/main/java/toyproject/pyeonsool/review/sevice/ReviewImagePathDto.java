package toyproject.pyeonsool.review.sevice;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.review.repository.ReviewImageDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewImagePathDto {
    private Long reviewId;
    private Long alcoholId;
    private AlcoholType type;
    private String imagePath;
    private LocalDateTime lastModifiedDate;
    private Byte grade;
    private String content;
    private Long recommendCount;
    private Long notRecommendCount;

    public static ReviewImagePathDto of(ReviewImageDto reviewImageDto, String imagePath) {
        return new ReviewImagePathDto(reviewImageDto.getReviewId(),
                reviewImageDto.getAlcoholId(),
                reviewImageDto.getType(),
                imagePath,
                reviewImageDto.getLastModifiedDate(),
                reviewImageDto.getGrade(),
                reviewImageDto.getContent(),
                reviewImageDto.getRecommendCount(),
                reviewImageDto.getNotRecommendCount()
        );
    }
}