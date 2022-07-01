package toyproject.pyeonsool.service;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.Review;

import java.time.LocalDateTime;

@Data
public class ReviewImageDto {
    private Long reviewId;
    private String fileName;
    private LocalDateTime lastModifiedDate;
    private Byte grade;
    private String content;
    private Long recommendCount;
    private Long notRecommendCount;

    @QueryProjection
    public ReviewImageDto(Long reviewId, String fileName, LocalDateTime lastModifiedDate, Byte grade, String content,
                          Long recommendCount, Long notRecommendCount) {
        this.reviewId = reviewId;
        this.fileName = fileName;
        this.lastModifiedDate = lastModifiedDate;
        this.grade = grade;
        this.content = content;
        this.recommendCount = recommendCount;
        this.notRecommendCount = notRecommendCount;
    }
}
