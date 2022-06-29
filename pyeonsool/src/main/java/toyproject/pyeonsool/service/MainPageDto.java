package toyproject.pyeonsool.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MainPageDto {
    private Long alcoholId;
    private String imagePath;
    private AlcoholType type;
    private String name;
    private Integer price;
    private Float abv;
    private List<String> keywords;
    private Long likeCount;

    public static MainPageDto of(
            Alcohol alcohol, String imagePath, List<String> alcoholKeywords,
            Long likeCount) {
        return new MainPageDto(
                alcohol.getId(),
                imagePath,
                alcohol.getType(),
                alcohol.getName(),
                alcohol.getPrice(),
                alcohol.getAbv(),
                alcoholKeywords,
                likeCount);
    }
}
