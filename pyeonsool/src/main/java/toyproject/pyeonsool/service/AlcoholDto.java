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
public class AlcoholDto {


    private AlcoholType type;
    private Long id;
    private String image;
    private String name;
    private Integer price;
    private String abv;
    private List<String> keywords;
    private List<String> vendors;

    private Long members;


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
    public AlcoholDto(Alcohol alcohol, String imagePath, List<String> alcoholKeywords, List<String> alcoholVendors, Long prferredMembers){
        type= alcohol.getType();
        id = alcohol.getId();
        image = imagePath;
        name = alcohol.getName();
        price = alcohol.getPrice();
        abv = String.valueOf(alcohol.getAbv());
        keywords = alcoholKeywords;
        vendors = alcoholVendors;
        members = prferredMembers;
    }





}
