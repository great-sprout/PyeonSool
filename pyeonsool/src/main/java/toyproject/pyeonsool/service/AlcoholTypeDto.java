package toyproject.pyeonsool.service;

import com.querydsl.core.annotations.QueryProjection;
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



    /*public AlcoholTypeDto (
            AlcoholType alcoholType, String imagePath, Integer price,String grade,String abvs, List<String> alcoholKeywords,
            List<String> alcoholVendors) {
            this.type = alcoholType;
            this.imagePath = imagePath;
            this.price=price;
            this.abv = abv;
            this.keywords = alcoholKeywords;
            this.vendors = alcoholVendors;
    }*/
@QueryProjection
    public AlcoholTypeDto(Alcohol alcohol){
        type= alcohol.getType();
        name = alcohol.getName();
        price = alcohol.getPrice();
        abv = String.valueOf(alcohol.getAbv());

    }





}
