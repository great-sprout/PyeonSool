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

            ArrayList<Alcohol> sojus = new ArrayList<>();
            setSojuList(sojus);

            for(Alcohol alcohol:sojus){
                em.persist(alcohol);
            }

            persistSojuKeywords(keywords, sojus);
            persistSojuVendors(sojus);

            ArrayList<Alcohol> beers = new ArrayList<>();
            setBeers(beers);
            for (Alcohol beer : beers) {
                em.persist(beer);
            }
            persistBeerKeywords(keywords, beers);
            persistBeerVendors(beers);

            em.flush();
            em.clear();
        }

        private void setBeers(ArrayList<Alcohol> beers) {
            beers.add(new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀"));
            beers.add(new Alcohol(BEER, "tiger.jpg", "타이거 아시안 라거", 2500,
                    5f, null, null, "아시아 퍼시픽 브루어리", "싱가포르"));
            beers.add(new Alcohol(BEER, "budweiser.png", "버드와이저", 2500,
                    5f, null, null, "앤하이저부시", "미국"));
            beers.add(new Alcohol(BEER, "gomyo.png", "곰표 밀맥주", 2500,
                    4.5f, null, null, "세븐브로이맥주", "한국"));
            beers.add(new Alcohol(BEER, "hite-extra-cold.png", "하이트 엑스트라 콜드", 2800,
                    4.5f, null, null, "하이트진로", "한국"));
            beers.add(new Alcohol(BEER, "kozel-premium-lager.png", "코젤 프리미엄 라거", 2500,
                    4.6f, null, null, "필젠스키 프레즈드로이", "체코"));
            beers.add(new Alcohol(BEER, "somersby.png", "서머스비(사과)", 4200,
                    4.5f, null, null, "칼스버그 서플라이", "덴마크"));
            beers.add(new Alcohol(BEER, "stella-artois.png", "스텔라 아르투아", 3000,
                    5f, null, null, "스텔라 아르투아 브루어리", "벨기에"));
            beers.add(new Alcohol(BEER, "guinness-draught.png", "기네스 드래프트", 3000,
                    4.2f, null, null, "디아지오 아일랜드", "아일랜드"));
        }

        private void persistSojuVendors(ArrayList<Alcohol> sojus) {
            em.persist(new Vendor(sojus.get(0), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(0), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(0), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(1), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(1), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(1), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(2), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(2), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(2), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(3), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(3), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(3), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(4), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(4), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(4), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(5), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(5), VendorName.valueOf("GS25")));

            em.persist(new Vendor(sojus.get(6), VendorName.valueOf("CU")));

            em.persist(new Vendor(sojus.get(7), VendorName.valueOf("GS25")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("GS25")));
        }

        private void setSojuList(ArrayList<Alcohol> sojus) {
            sojus.add(new Alcohol(SOJU, "jinro.jpg",
                    "진로 이즈 백", 1800, 16.5f, null,null, "하이트 진로(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "jamong-chamisul.jpg",
                    "자몽에 이슬", 1900, 13f, null, null,"하이트 진로(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "chamisul.png",
                    "참이슬", 1950, 16.5f, null,null, "하이트 진로(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "like-first.jpg",
                    "처음처럼", 1950, 16.5f, null,null, "롯데칠성음료(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "hanlla.png",
                    "한라산", 1900, 17.5f, null, null,"(주)한라산", "대한민국"));
            sojus.add(new Alcohol(SOJU, "rabbit-white.jpg",
                    "토끼 소주 화이트", 38000, 23f, null, null,"농업회사법인 토끼소주(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "munbae.jpg",
                    "문배술", 10260, 40f, null, null,"문배주양조원", "대한민국"));
            sojus.add(new Alcohol(SOJU, "gosori.png",
                    "제주 고소리술", 22800, 29f, null, null,"제주샘영농조합", "대한민국"));
            sojus.add(new Alcohol(SOJU, "hwayo.jpg",
                    "화요", 8500, 25f, null,null, "(주)화요", "대한민국"));
        }

        private void persistSojuKeywords(Keyword[] keywords, ArrayList<Alcohol> sojus) {
            em.persist(new AlcoholKeyword(keywords[1], sojus.get(0)));
            em.persist(new AlcoholKeyword(keywords[2], sojus.get(0)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(0)));

            em.persist(new AlcoholKeyword(keywords[0],sojus.get(1)));
            em.persist(new AlcoholKeyword(keywords[6],sojus.get(1)));
            em.persist(new AlcoholKeyword(keywords[16],sojus.get(1)));

            em.persist(new AlcoholKeyword(keywords[1],sojus.get(2)));
            em.persist(new AlcoholKeyword(keywords[2],sojus.get(2)));
            em.persist(new AlcoholKeyword(keywords[11],sojus.get(2)));

            em.persist(new AlcoholKeyword(keywords[1],sojus.get(3)));
            em.persist(new AlcoholKeyword(keywords[2],sojus.get(3)));
            em.persist(new AlcoholKeyword(keywords[11],sojus.get(3)));

            em.persist(new AlcoholKeyword(keywords[1],sojus.get(4)));
            em.persist(new AlcoholKeyword(keywords[2],sojus.get(4)));
            em.persist(new AlcoholKeyword(keywords[11],sojus.get(4)));

            em.persist(new AlcoholKeyword(keywords[0],sojus.get(5)));
            em.persist(new AlcoholKeyword(keywords[1],sojus.get(5)));
            em.persist(new AlcoholKeyword(keywords[10],sojus.get(5)));

            em.persist(new AlcoholKeyword(keywords[0],sojus.get(6)));
            em.persist(new AlcoholKeyword(keywords[1],sojus.get(6)));
            em.persist(new AlcoholKeyword(keywords[5],sojus.get(6)));
            em.persist(new AlcoholKeyword(keywords[10],sojus.get(6)));

            em.persist(new AlcoholKeyword(keywords[1],sojus.get(7)));
            em.persist(new AlcoholKeyword(keywords[10],sojus.get(7)));
            
            em.persist(new AlcoholKeyword(keywords[1],sojus.get(8)));
            em.persist(new AlcoholKeyword(keywords[5],sojus.get(8)));
            em.persist(new AlcoholKeyword(keywords[10],sojus.get(8)));
        }
        private void persistBeerKeywords(Keyword[] keywords, ArrayList<Alcohol> beers) {
            em.persist(new AlcoholKeyword(keywords[17], beers.get(0)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(0)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(0)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(1)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(1)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(1)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(2)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(2)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(2)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(3)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(3)));
            em.persist(new AlcoholKeyword(keywords[16], beers.get(3)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(4)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(4)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(4)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(5)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(5)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(5)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(6)));
            em.persist(new AlcoholKeyword(keywords[16], beers.get(6)));
            em.persist(new AlcoholKeyword(keywords[0], beers.get(6)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(7)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(7)));
            em.persist(new AlcoholKeyword(keywords[7], beers.get(7)));

            em.persist(new AlcoholKeyword(keywords[17], beers.get(8)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(8)));
            em.persist(new AlcoholKeyword(keywords[9], beers.get(8)));
        }

        private void persistBeerVendors(ArrayList<Alcohol> beers) {
            for (Alcohol beer : beers) {
                em.persist(new Vendor(beer, VendorName.CU));
                em.persist(new Vendor(beer, VendorName.GS25));
                em.persist(new Vendor(beer, VendorName.SEVEN_ELEVEN));
            }
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
