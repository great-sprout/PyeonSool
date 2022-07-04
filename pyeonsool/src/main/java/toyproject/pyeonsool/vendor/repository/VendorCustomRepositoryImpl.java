package toyproject.pyeonsool.vendor.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static toyproject.pyeonsool.domain.QVendor.vendor;

@RequiredArgsConstructor
public class VendorCustomRepositoryImpl implements VendorCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> getAlcoholVendors(Long alcoholId) {
        return queryFactory.select(vendor.name.stringValue())
                .from(vendor)
                .where(vendor.alcohol.id.eq(alcoholId))
                .fetch();
    }
}
