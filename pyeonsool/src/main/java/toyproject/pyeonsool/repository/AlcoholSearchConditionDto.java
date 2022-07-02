package toyproject.pyeonsool.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.VendorName;

import java.util.List;

@Data
@AllArgsConstructor
public class AlcoholSearchConditionDto {

    private AlcoholType alcoholType;
    private List<String> keywords;
    private String search;
    private VendorName vendorName;
    private String sortType;
    private String standard;
}
