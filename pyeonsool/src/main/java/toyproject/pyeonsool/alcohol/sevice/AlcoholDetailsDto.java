package toyproject.pyeonsool.alcohol.sevice;

import lombok.*;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private String grade;
    private List<String> keywords;
    private List<String> vendors;
    private boolean likeCurrentAlcohol;

    public static AlcoholDetailsDto of(
            Alcohol alcohol, String imagePath, String grade, List<String> alcoholKeywords,
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
                grade,
                alcoholKeywords,
                alcoholVendors,
                likeCurrentAlcohol);
    }

    public static AlcoholDetailsDto of(
            Alcohol alcohol, String imagePath, String grade, List<String> alcoholKeywords, List<String> alcoholVendors) {
        return AlcoholDetailsDto.of(alcohol, imagePath, grade, alcoholKeywords, alcoholVendors, false);
    }
}
