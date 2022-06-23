package toyproject.pyeonsool.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlcoholDetailsDto {
    private AlcoholType type;
    private String imagePath;
    private String name;
    private Integer price;
    private Float abv;
    private Byte sugarContent;
    private Byte body;
    private String manufacturer;
    private String origin;
    private boolean likeCurrentAlcohol;

    public static AlcoholDetailsDto of(Alcohol alcohol, String imagePath, boolean likeCurrentAlcohol) {
        return new AlcoholDetailsDto(
                alcohol.getType(),
                imagePath,
                alcohol.getName(),
                alcohol.getPrice(),
                alcohol.getAbv(),
                alcohol.getSugarContent(),
                alcohol.getBody(),
                alcohol.getManufacturer(),
                alcohol.getOrigin(),
                likeCurrentAlcohol);
    }
}
