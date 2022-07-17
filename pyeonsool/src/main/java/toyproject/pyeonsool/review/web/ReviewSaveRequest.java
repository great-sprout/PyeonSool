package toyproject.pyeonsool.review.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewSaveRequest {

    private Long alcoholId;
    private Byte grade;
    private String content;

    public ReviewSaveRequest(Long alcoholId) {
        this.alcoholId = alcoholId;
    }
}
