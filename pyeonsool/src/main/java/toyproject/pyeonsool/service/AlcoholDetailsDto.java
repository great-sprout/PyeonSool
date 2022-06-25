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
public class AlcoholDetailsDto {
    private AlcoholType type;
    private String imagePath;
    private String name;
    private Integer price;
    private String abv;
    private Byte sugarContent;
    private Byte body;
    private String manufacturer;
    private String origin;
    private List<String> keywords;
    private List<String> vendors;
    private boolean likeCurrentAlcohol;

    public static AlcoholDetailsDto of(
            Alcohol alcohol, String imagePath, List<String> alcoholKeywords,
            List<String> alcoholVendors, boolean likeCurrentAlcohol) {
        return new AlcoholDetailsDto(
                alcohol.getType(),
                imagePath,
                alcohol.getName(),
                alcohol.getPrice(),
                String.format("%.1f", alcohol.getAbv()),
                alcohol.getSugarContent(),
                alcohol.getBody(),
                alcohol.getManufacturer(),
                alcohol.getOrigin(),
                alcoholKeywords,
                alcoholVendors,
                likeCurrentAlcohol);
    }

    public static AlcoholDetailsDto of(
            Alcohol alcohol, String imagePath, List<String> alcoholKeywords, List<String> alcoholVendors) {
        return AlcoholDetailsDto.of(alcohol, imagePath, alcoholKeywords, alcoholVendors, false);
    }
}
