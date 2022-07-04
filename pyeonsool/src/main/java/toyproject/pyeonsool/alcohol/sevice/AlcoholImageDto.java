package toyproject.pyeonsool.alcohol.sevice;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

//마이페이지 내 Like리스트 부분 정보
@Data
public class AlcoholImageDto {
    private Long alcoholId;
    private String imagePath;

    @QueryProjection
    public AlcoholImageDto(Long alcoholId, String imagePath) {
        this.alcoholId = alcoholId;
        this.imagePath = imagePath;
    }
}
