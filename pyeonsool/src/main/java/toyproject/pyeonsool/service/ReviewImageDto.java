package toyproject.pyeonsool.service;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.domain.Review;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewImageDto {
    private Long reviewId;
    private String image;
    private LocalDateTime lastModifiedDate;
    private Byte grade;
    private String content;
    private Long recommendCount;
    private Long notRecommendCount;


    @QueryProjection
    public ReviewImageDto (Review review, String imagePath) {
                reviewId = review.getId();
                image = imagePath;
                lastModifiedDate = review.getLastModifiedDate();
                grade = review.getGrade();
                content = review.getContent();
                recommendCount = review.getRecommendCount();
                notRecommendCount = review.getNotRecommendCount();
    }
}
