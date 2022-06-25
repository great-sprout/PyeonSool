package toyproject.pyeonsool.repository;

import java.util.List;

public interface VendorCustomRepository {
    List<String> getAlcoholVendors(Long alcoholId);
}


