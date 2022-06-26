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
public class AlcoholTypeDto {

    private AlcoholType type;
    private String imagePath;
    private String name;
    private Integer price;
    private String abv;
    private List<String> keywords;
    private List<String> vendors;



    public static AlcoholTypeDto of(
            Alcohol alcohol, String imagePath, String grade, List<String> alcoholKeywords,
            List<String> alcoholVendors) {
        return new AlcoholTypeDto(
                alcohol.getType(),
                imagePath,
                alcohol.getName(),
                alcohol.getPrice(),
                String.format("%.1f", alcohol.getAbv()),
                alcoholKeywords,
                alcoholVendors);
    }
    public static AlcoholTypeDto of(AlcoholType type, String imagePath, String name, Integer price, String format, List<String> alcoholKeywords, List<String> alcoholVendors) {
        return AlcoholTypeDto.of(type,imagePath,name,price,format,alcoholKeywords,alcoholVendors);
    }

}
