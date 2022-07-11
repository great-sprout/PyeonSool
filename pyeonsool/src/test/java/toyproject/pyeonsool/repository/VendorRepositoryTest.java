package toyproject.pyeonsool.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.pyeonsool.AppConfig;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.vendor.repository.VendorRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static toyproject.pyeonsool.domain.AlcoholType.BEER;
import static toyproject.pyeonsool.domain.VendorName.*;

@DataJpaTest
@Import(AppConfig.class)
class VendorRepositoryTest {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    EntityManager em;

    @Test
    void should_Success_When_VendorsAreObtained() {
        //given
        Alcohol alcohol = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        em.persist(alcohol);

        em.persist(new Vendor(alcohol, CU));
        em.persist(new Vendor(alcohol, GS25));
        em.persist(new Vendor(alcohol, SEVEN_ELEVEN));

        //then
        List<String> alcoholVendors = vendorRepository.getAlcoholVendors(alcohol.getId());

        //then
        Assertions.assertThat(alcoholVendors).containsOnly(CU.name(), GS25.name(), SEVEN_ELEVEN.name());
    }
}