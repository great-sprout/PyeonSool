package toyproject.pyeonsool.vendor.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.pyeonsool.alcohol.domain.Alcohol;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vendor {

    @Id
    @GeneratedValue
    @Column(name = "vendor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;

    @Enumerated(value = EnumType.STRING)
    private VendorName name;

    public Vendor(Alcohol alcohol, VendorName name) {
        this.alcohol = alcohol;
        this.name = name;
    }
}
