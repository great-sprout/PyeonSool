package toyproject.pyeonsool.alcohol.web;

import lombok.Data;

import java.util.List;

@Data
public class AlcoholSearchForm {
    private String alcoholType;
    private String search;
    private List<String> keywords;
    private String vendorName;
    private String bySort;
}
