package toyproject.pyeonsool.review.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewEditRequest {
    private Byte grade;
    private String content;
}
