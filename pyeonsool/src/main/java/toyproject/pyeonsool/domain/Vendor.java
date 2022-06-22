package toyproject.pyeonsool.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long alcoholId;

    @Enumerated(value = EnumType.STRING)
    private VendorName name;

    public Vendor(Long alcoholId, VendorName name) {
        this.alcoholId = alcoholId;
        this.name = name;
    }
}
