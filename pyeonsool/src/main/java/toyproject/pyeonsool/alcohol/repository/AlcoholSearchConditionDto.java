package toyproject.pyeonsool.alcohol.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.vendor.domain.VendorName;

import java.util.List;

@Data
@AllArgsConstructor
public class AlcoholSearchConditionDto {

    private AlcoholType alcoholType;
    private List<String> keywords;
    private String search;
    private VendorName vendorName;
}
