package toyproject.pyeonsool.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRecommendCountResponse {
    private Long likeCount;
    private Long dislikeCount;
}
