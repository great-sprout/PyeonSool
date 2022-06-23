package toyproject.pyeonsool;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import toyproject.pyeonsool.domain.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;

import static toyproject.pyeonsool.domain.AlcoholType.*;

@Profile("local")
@Component
@RequiredArgsConstructor
public class initDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    static class InitService {

        @PersistenceContext
        EntityManager em;

        public void init() {
            Keyword[] keywords = new Keyword[21];
            setKeywords(keywords);

            for (Keyword keyword : keywords) {
                em.persist(keyword);
            }

            ArrayList<Alcohol> alcohols = new ArrayList<>();
            alcohols.add(new Alcohol(WINE, "louis-jadot.png",
                    "루이자도 부르고뉴 샤르도네", 35000, 13f, null, null,null, null));

            em.persist(alcohols.get(0));

            em.persist(new AlcoholKeyword(keywords[0], alcohols.get(0)));
            em.persist(new AlcoholKeyword(keywords[1], alcohols.get(0)));


            alcohols.add(new Alcohol(SOJU, "jinro.jpg",
                    "진로 이즈 백", 1800, 16.5f, null,null, "하이트 진로(주)", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "jamong-chamisul.jpg",
                    "자몽에 이슬", 1900, 13f, null, null,"하이트 진로(주)", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "chamisul.png",
                    "참이슬", 1950, 16.5f, null,null, "하이트 진로(주)", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "like-first.jpg",
                    "처음처럼", 1950, 16.5f, null,null, "롯데칠성음료(주)", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "hanlla.png",
                    "한라산", 1900, 17.5f, null, null,"(주)한라산", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "rabbit-white.jpg",
                    "토끼 소주 화이트", 38000, 23f, null, null,"농업회사법인 토끼소주(주)", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "munbae.jpg",
                    "문배술", 10260, 40f, null, null,"문배주양조원", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "gosori.png",
                    "제주 고소리술", 22800, 29f, null, null,"제주샘영농조합", "대한민국"));
            alcohols.add(new Alcohol(SOJU, "hwayo.jpg",
                    "화요", 8500, 25f, null,null, "(주)화요", "대한민국"));

            em.persist(alcohols.get(0));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(0)));
            em.persist(new AlcoholKeyword(keywords[2],alcohols.get(0)));
            em.persist(new AlcoholKeyword(keywords[11],alcohols.get(0)));
            em.persist(new Vendor(alcohols.get(0), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(0), VendorName.valueOf("GS25")));
            em.persist(new Vendor(alcohols.get(0), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(alcohols.get(1));
            em.persist(new AlcoholKeyword(keywords[0],alcohols.get(1)));
            em.persist(new AlcoholKeyword(keywords[6],alcohols.get(1)));
            em.persist(new AlcoholKeyword(keywords[16],alcohols.get(1)));
            em.persist(new Vendor(alcohols.get(1), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(1), VendorName.valueOf("GS25")));
            em.persist(new Vendor(alcohols.get(1), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(alcohols.get(2));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(2)));
            em.persist(new AlcoholKeyword(keywords[2],alcohols.get(2)));
            em.persist(new AlcoholKeyword(keywords[11],alcohols.get(2)));
            em.persist(new Vendor(alcohols.get(2), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(2), VendorName.valueOf("GS25")));
            em.persist(new Vendor(alcohols.get(2), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(alcohols.get(3));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(3)));
            em.persist(new AlcoholKeyword(keywords[2],alcohols.get(3)));
            em.persist(new AlcoholKeyword(keywords[11],alcohols.get(3)));
            em.persist(new Vendor(alcohols.get(3), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(3), VendorName.valueOf("GS25")));
            em.persist(new Vendor(alcohols.get(3), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(alcohols.get(4));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(4)));
            em.persist(new AlcoholKeyword(keywords[2],alcohols.get(4)));
            em.persist(new AlcoholKeyword(keywords[11],alcohols.get(4)));
            em.persist(new Vendor(alcohols.get(4), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(4), VendorName.valueOf("GS25")));
            em.persist(new Vendor(alcohols.get(4), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(alcohols.get(5));
            em.persist(new AlcoholKeyword(keywords[0],alcohols.get(5)));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(5)));
            em.persist(new AlcoholKeyword(keywords[10],alcohols.get(5)));
            em.persist(new Vendor(alcohols.get(5), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(5), VendorName.valueOf("GS25")));

            em.persist(alcohols.get(6));
            em.persist(new AlcoholKeyword(keywords[0],alcohols.get(6)));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(6)));
            em.persist(new AlcoholKeyword(keywords[5],alcohols.get(6)));
            em.persist(new AlcoholKeyword(keywords[10],alcohols.get(6)));
            em.persist(new Vendor(alcohols.get(6), VendorName.valueOf("CU")));

            em.persist(alcohols.get(7));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(7)));
            em.persist(new AlcoholKeyword(keywords[10],alcohols.get(7)));
            em.persist(new Vendor(alcohols.get(7), VendorName.valueOf("GS25")));

            em.persist(alcohols.get(8));
            em.persist(new AlcoholKeyword(keywords[1],alcohols.get(8)));
            em.persist(new AlcoholKeyword(keywords[5],alcohols.get(8)));
            em.persist(new AlcoholKeyword(keywords[10],alcohols.get(8)));
            em.persist(new Vendor(alcohols.get(8), VendorName.valueOf("CU")));
            em.persist(new Vendor(alcohols.get(8), VendorName.valueOf("GS25")));



            em.flush();
            em.clear();
        }

        private void setKeywords(Keyword[] keywords) {
            String[] keywordNames = {"sweet", "clear", "cool", "heavy", "light",
                                     "nutty", "fresh", "flower", "bitter", "unique",
                                     "strong", "middle", "mild", "white", "red",
                                     "astringent", "fruit", "nonAlcoholic", "highAcidity", "middleAcidity",
                                     "lowAcidity"
                                    };

            for(int i = 0; i < keywords.length; i++){
                keywords[i] = new Keyword(keywordNames[i]);
            }

        }
    }
}
