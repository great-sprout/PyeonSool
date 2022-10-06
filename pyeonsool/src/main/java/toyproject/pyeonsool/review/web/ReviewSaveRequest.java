package toyproject.pyeonsool.review.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class ReviewSaveRequest {

    @NotNull(message = "술 고유 번호는 필수입니다.")
    @Positive(message = "술 고유 번호는 0보다 커야합니다.")
    private Long alcoholId;

    @NotNull(message = "평점은 필수입니다.")
    @Range(min = 1, max = 5, message = "평점은 1 ~ 5 사이로 선택하세요.")
    private Byte grade;

    @NotBlank(message = "리뷰는 공백일 수 없습니다.")
    @Length(max = 300, message = "리뷰는 300자 이내로 작성하세요.")
    private String content;

    public ReviewSaveRequest(Long alcoholId) {
        this.alcoholId = alcoholId;
    }
}
