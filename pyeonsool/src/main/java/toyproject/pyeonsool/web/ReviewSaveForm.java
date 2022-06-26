package toyproject.pyeonsool.web;

import lombok.Data;

@Data
public class ReviewSaveForm {
    private Long alcoholId;
    private Byte grade;
    private String content;
}
