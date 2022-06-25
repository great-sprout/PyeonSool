package toyproject.pyeonsool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.pyeonsool.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>, VendorCustomRepository {
}
