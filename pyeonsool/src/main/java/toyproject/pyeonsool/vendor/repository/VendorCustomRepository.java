package toyproject.pyeonsool.vendor.repository;

import java.util.List;

public interface VendorCustomRepository {
    List<String> getAlcoholVendors(Long alcoholId);
}


