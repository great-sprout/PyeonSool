package toyproject.pyeonsool.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.VendorName;

import java.util.List;

public interface AlcoholCustomRepository {
    Page<Alcohol> findAllByType(AlcoholType alcoholType, Pageable pageable,
                                List<String> keywords, String search, VendorName vendorName);
}
