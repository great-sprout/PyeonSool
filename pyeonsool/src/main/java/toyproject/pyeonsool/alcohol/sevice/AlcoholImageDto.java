package toyproject.pyeonsool.alcohol.sevice;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

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
