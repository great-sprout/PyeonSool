package toyproject.pyeonsool.alcohol.domain;

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
    private Byte body;

    private String manufacturer;
    private String origin;

    private Long likeCount;

    public Alcohol(AlcoholType type, String fileName, String name, Integer price, Float abv, Byte sugarContent,
                   Byte body, String manufacturer, String origin) {
        this(type, fileName, name, price, abv, sugarContent, body, manufacturer, origin, 0L);
    }

    public Alcohol(AlcoholType type, String fileName, String name, Integer price, Float abv, Byte sugarContent, Byte body, String manufacturer, String origin, Long likeCount) {
        this.type = type;
        this.fileName = fileName;
        this.name = name;
        this.price = price;
        this.abv = abv;
        this.sugarContent = sugarContent;
        this.body = body;
        this.manufacturer = manufacturer;
        this.origin = origin;
        this.likeCount = likeCount;
    }

    public void plusLikeCount() {
        this.likeCount += 1;
    }

    public void minusLikeCount() {
        if (this.likeCount != 0) {
            this.likeCount -= 1;
        }
    }
}
