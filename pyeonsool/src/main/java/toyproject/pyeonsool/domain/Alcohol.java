package toyproject.pyeonsool.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(name = "alcohol_name_uk", columnNames = {"name"})})
public class Alcohol {
    @Id
    @GeneratedValue
    @Column(name = "alcohol_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AlcoholType type;

    @Column(length = 100)
    private String fileName;

    @Column(length = 200)
    private String name;
    private Integer price;

    @Column(precision = 3, scale = 1)
    private Float abv;
    private Byte sugarContent;

    private String manufacturer;
    private String origin;

    public Alcohol(AlcoholType type, String fileName, String name, Integer price, Float abv, Byte sugarContent, String manufacturer, String origin) {
        this.type = type;
        this.fileName = fileName;
        this.name = name;
        this.price = price;
        this.abv = abv;
        this.sugarContent = sugarContent;
        this.manufacturer = manufacturer;
        this.origin = origin;
    }
}
