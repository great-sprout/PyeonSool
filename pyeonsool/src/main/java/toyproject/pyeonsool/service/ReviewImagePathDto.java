package toyproject.pyeonsool.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.Alcohol;

@Data
@AllArgsConstructor
public class ReviewImagePathDto {
    private Long alcoholId;
    private String imagePath;


    public static ReviewImagePathDto of(Alcohol alcohol, String imagePath){
        return new ReviewImagePathDto(
                alcohol.getId(),
                imagePath
        );
        }
}