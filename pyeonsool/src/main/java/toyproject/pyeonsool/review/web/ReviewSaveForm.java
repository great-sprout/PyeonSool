package toyproject.pyeonsool.review.web;

import lombok.Data;

@Data
public class ReviewSaveForm {
    private Long alcoholId;
    private Byte grade;
    private String content;

    public ReviewSaveForm(Long alcoholId) {
        this.alcoholId = alcoholId;
    }
}
