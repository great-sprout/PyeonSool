package toyproject.pyeonsool.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.vendor.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>, VendorCustomRepository {
}
