package toyproject.pyeonsool.service;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.Review;

import java.time.LocalDateTime;

@Data
public class ReviewImageDto {
    private Long reviewId;
    private Long alcoholId;
    private AlcoholType type;
    private String fileName;
    private LocalDateTime lastModifiedDate;
    private Byte grade;
    private String content;
    private Long recommendCount;
    private Long notRecommendCount;

    @QueryProjection
    public ReviewImageDto(Long reviewId, Long alcoholId, AlcoholType type, String fileName,
                          LocalDateTime lastModifiedDate, Byte grade, String content, Long recommendCount,
                          Long notRecommendCount) {
        this.reviewId = reviewId;
        this.alcoholId = alcoholId;
        this.type = type;
        this.fileName = fileName;
        this.lastModifiedDate = lastModifiedDate;
        this.grade = grade;
        this.content = content;
        this.recommendCount = recommendCount;
        this.notRecommendCount = notRecommendCount;
    }
}
