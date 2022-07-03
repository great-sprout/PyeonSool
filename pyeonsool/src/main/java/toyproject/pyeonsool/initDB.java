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
import java.util.List;

import static toyproject.pyeonsool.domain.AlcoholType.*;

@Profile("server")
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

            Member[] members = new Member[50];
            setMembers(members);
            for (Member m : members) {
                em.persist(m);
            }

            Keyword[] keywords = new Keyword[21];
            setKeywords(keywords);
            for (Keyword keyword : keywords) {
                em.persist(keyword);
            }

            ArrayList<Alcohol> sojus = new ArrayList<>();
            setSojuList(sojus);

            for (Alcohol alcohol : sojus) {
                em.persist(alcohol);
            }

            persistSojuKeywords(keywords, sojus);
            persistSojuVendors(sojus);

            List<Review> reviews = new ArrayList<>();
            setReviews(members, sojus.get(0), reviews);
            for (Review review : reviews) {
                em.persist(review);
            }

            ArrayList<Alcohol> beers = new ArrayList<>();
            setBeers(beers);
            for (Alcohol beer : beers) {
                em.persist(beer);
            }
            persistBeerKeywords(keywords, beers);
            persistBeerVendors(beers);

            //와인 데이터 넣기
            ArrayList<Alcohol> wines = new ArrayList<>();
            setWines(wines);
            for (Alcohol alcohol : wines) {
                em.persist(alcohol);
            }
            persistWineKeywords(keywords, wines);
            persistWineVendors(wines);

            PreferredAlcohol[] preferredAlcohols = new PreferredAlcohol[21];
            setPreferredAlcohols(members, sojus, beers, wines, preferredAlcohols);
            for (PreferredAlcohol preferredAlcohol : preferredAlcohols) {
                em.persist(preferredAlcohol);
            }

            for (int i = 0, len = Math.min(members.length, reviews.size()); i < len; i++) {
                int j = 0;
                for (; j < i; j++) {
                    em.persist(new RecommendedReview(members[i], reviews.get(j), RecommendStatus.LIKE));
                }
                for (; j < len; j++) {
                    em.persist(new RecommendedReview(members[i], reviews.get(j), RecommendStatus.DISLIKE));
                }
            }
            MyKeyword memberKeyword1 = new MyKeyword(members[0],keywords[0]);
            MyKeyword memberKeyword2 = new MyKeyword(members[0],keywords[1]);
            MyKeyword memberKeyword3 = new MyKeyword(members[0],keywords[2]);
            MyKeyword memberKeyword4 = new MyKeyword(members[1],keywords[3]);
            MyKeyword memberKeyword5 = new MyKeyword(members[1],keywords[4]);
            MyKeyword memberKeyword6 = new MyKeyword(members[1],keywords[5]);
            MyKeyword memberKeyword7 = new MyKeyword(members[2],keywords[6]);
            MyKeyword memberKeyword8 = new MyKeyword(members[2],keywords[7]);
            MyKeyword memberKeyword9 = new MyKeyword(members[2],keywords[8]);
            MyKeyword memberKeyword10 = new MyKeyword(members[3],keywords[9]);
            MyKeyword memberKeyword11 = new MyKeyword(members[3],keywords[10]);
            MyKeyword memberKeyword12 = new MyKeyword(members[3],keywords[11]);

            em.persist(memberKeyword1);
            em.persist(memberKeyword2);
            em.persist(memberKeyword3);
            em.persist(memberKeyword4);
            em.persist(memberKeyword5);
            em.persist(memberKeyword6);
            em.persist(memberKeyword7);
            em.persist(memberKeyword8);
            em.persist(memberKeyword9);
            em.persist(memberKeyword10);
            em.persist(memberKeyword11);
            em.persist(memberKeyword12);

            em.flush();
            em.clear();
        }

        private void setReviews(Member[] members, Alcohol alcohol, List<Review> reviews) {
            reviews.add(new Review(members[1], alcohol, (byte) 5, "목넘김이 시원하네요!", 4L, 1L));
            reviews.add(new Review(members[2], alcohol, (byte) 3, "평범하네요", 3L, 2L));
            reviews.add(new Review(members[0], alcohol, (byte) 2,
                    "그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. " +
                            "그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. " +
                            "그저 그래요. 그저 그래요. 그저 그래요. 그저 그래요. ", 2L, 3L));
            reviews.add(new Review(members[1], alcohol, (byte) 5, "너무 좋아요", 1L, 4L));
            reviews.add(new Review(members[2], alcohol, (byte) 1, "최악의 술입니다...", 0L, 5L));
            for (int i = 0; i < 65; i++) {
                reviews.add(new Review(
                        members[i % 3], alcohol, (byte) (5 - (i % 5)), "테스트 리뷰 " + i));
            }
        }

        private void setPreferredAlcohols(Member[] members, ArrayList<Alcohol> sojus, ArrayList<Alcohol> beers, ArrayList<Alcohol> wines, PreferredAlcohol[] preferredAlcohols) {
            preferredAlcohols[0] = new PreferredAlcohol(members[0], sojus.get(0));
            preferredAlcohols[1] = new PreferredAlcohol(members[1], sojus.get(0));
            preferredAlcohols[2] = new PreferredAlcohol(members[2], sojus.get(0));
            preferredAlcohols[3] = new PreferredAlcohol(members[0], sojus.get(1));
            preferredAlcohols[4] = new PreferredAlcohol(members[2], beers.get(1));
            preferredAlcohols[5] = new PreferredAlcohol(members[1], beers.get(2));
            preferredAlcohols[6] = new PreferredAlcohol(members[3], beers.get(0));
            preferredAlcohols[7] = new PreferredAlcohol(members[4], beers.get(0));
            preferredAlcohols[8] = new PreferredAlcohol(members[1], wines.get(2));
            preferredAlcohols[9] = new PreferredAlcohol(members[2], wines.get(3));
            preferredAlcohols[10] = new PreferredAlcohol(members[3], wines.get(4));
            preferredAlcohols[11] = new PreferredAlcohol(members[4], wines.get(6));
            preferredAlcohols[12] = new PreferredAlcohol(members[3], sojus.get(6));
            preferredAlcohols[13] = new PreferredAlcohol(members[4], sojus.get(7));
            preferredAlcohols[14] = new PreferredAlcohol(members[4], beers.get(6));

            preferredAlcohols[15] = new PreferredAlcohol(members[0], beers.get(2));
            preferredAlcohols[16] = new PreferredAlcohol(members[1], beers.get(6));
            preferredAlcohols[17] = new PreferredAlcohol(members[2], sojus.get(1));
            preferredAlcohols[18] = new PreferredAlcohol(members[3], sojus.get(7));
            preferredAlcohols[19] = new PreferredAlcohol(members[4], sojus.get(3));
            preferredAlcohols[20] = new PreferredAlcohol(members[0], sojus.get(5));
        }

        private void setMembers(Member[] members) {
            members[0] = new Member("준영이", "chlwnsdud121", "1234", "01012345678");
            members[1] = new Member("영준이", "chldudwns121", "1234", "01023456789");
            members[2] = new Member("춘향이", "chlcnsgid121", "1234", "01043218765");
            members[3] = new Member("지환이", "tlswlghks121", "1234", "01012341234");
            members[4] = new Member("몽룡이", "dlahdfyd121", "1234", "01043214321");

            String[] nicknames = new String[]{
                    "연욱이", "수빈이", "다솜", "다온", "도담",
                    "든해", "마리", "마루한", "마루", "맑은가람",
                    "미리별", "미리", "미르", "밝음", "보예",
                    "바론", "별찬", "별하", "아라", "엄지",
                    "효림", "우람", "윤슬", "슬옹", "이든",
                    "알", "예님", "우솔", "진아", "한울",
                    "해은", "세비", "세화", "후연", "규서"};

            for (int i = 0; i < nicknames.length; i++) {
                members[5 + i] = new Member(nicknames[i], "testId" + i, "1234", "010123456" + i);
            }
        }

        private void setSojuList(ArrayList<Alcohol> sojus) {
            sojus.add(new Alcohol(SOJU, "jinro.jpg",
                    "진로 이즈 백", 1800, 16.5f, null, null, "하이트 진로(주)", "대한민국",3L));
            sojus.add(new Alcohol(SOJU, "jamong-chamisul.jpg",
                    "자몽에 이슬", 1900, 13f, null, null, "하이트 진로(주)", "대한민국",2L));
            sojus.add(new Alcohol(SOJU, "chamisul.png",
                    "참이슬", 1950, 16.5f, null, null, "하이트 진로(주)", "대한민국",0L));
            sojus.add(new Alcohol(SOJU, "like-first.jpg",
                    "처음처럼", 1950, 16.5f, null, null, "롯데칠성음료(주)", "대한민국",1L));
            sojus.add(new Alcohol(SOJU, "hanlla.png",
                    "한라산", 1900, 17.5f, null, null, "(주)한라산", "대한민국",0L));
            sojus.add(new Alcohol(SOJU, "rabbit-white.jpg",
                    "토끼 소주 화이트", 38000, 23f, null, null, "농업회사법인 토끼소주(주)", "대한민국",1L));
            sojus.add(new Alcohol(SOJU, "munbae.jpg",
                    "문배술", 10260, 40f, null, null, "문배주양조원", "대한민국",1L));
            sojus.add(new Alcohol(SOJU, "gosori.png",
                    "제주 고소리술", 22800, 29f, null, null, "제주샘영농조합", "대한민국",2L));
            sojus.add(new Alcohol(SOJU, "hwayo.jpg",
                    "화요", 8500, 25f, null, null, "(주)화요", "대한민국",0L));

            sojus.add(new Alcohol(SOJU, "grape-chamisul.png",
                    "청포도에 이슬", 1900,13f, null, null, "하이트진로(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "merona-chamisul.png",
                    "메로나에 이슬", 1700,12f, null, null, "하이트진로(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "rabbit-black.jpg",
                    "토기 소주 블랙", 40000,40f, null, null, "농업회사법인 토끼소주(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "secret-talk-24.jpg",
                    "밀담 24도 ", 11500,24f, null, null, "농업회사법인 (주)착한농부", "대한민국"));
            sojus.add(new Alcohol(SOJU, "ryeo.jpg",
                    "국순당려", 15000,25f, null, null, "국순당여주명주", "대한민국"));
            sojus.add(new Alcohol(SOJU, "barracks.png",
                    "병영 소주", 36960,40f, null, null, "병영양조장", "대한민국"));
            sojus.add(new Alcohol(SOJU, "andong.jpg",
                    "안동 소주 일품", 22800,21f, null, null, "안동소주일품(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "chungha.jpg",
                    "청하", 2750,13f, null, null, "롯데칠성음료(주)", "대한민국"));
            sojus.add(new Alcohol(SOJU, "seoul-night.png",
                    "서울의밤", 7200,13f, null, null, "(주)더한주류", "대한민국"));
            sojus.add(new Alcohol(SOJU, "myeongin-andong.jpg",
                    "명인안동소주", 8500,35f, null, null, "명인안동소주", "대한민국"));
            sojus.add(new Alcohol(SOJU, "gold-barley.png",
                    "황금보리", 6200,17f, null, null, "황금보리(유)", "대한민국"));
        }

        private void persistSojuKeywords(Keyword[] keywords, ArrayList<Alcohol> sojus) {
            em.persist(new AlcoholKeyword(keywords[1], sojus.get(0)));
            em.persist(new AlcoholKeyword(keywords[2], sojus.get(0)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(0)));

            em.persist(new AlcoholKeyword(keywords[0], sojus.get(1)));
            em.persist(new AlcoholKeyword(keywords[6], sojus.get(1)));
            em.persist(new AlcoholKeyword(keywords[16], sojus.get(1)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(2)));
            em.persist(new AlcoholKeyword(keywords[2], sojus.get(2)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(2)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(3)));
            em.persist(new AlcoholKeyword(keywords[2], sojus.get(3)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(3)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(4)));
            em.persist(new AlcoholKeyword(keywords[2], sojus.get(4)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(4)));

            em.persist(new AlcoholKeyword(keywords[0], sojus.get(5)));
            em.persist(new AlcoholKeyword(keywords[1], sojus.get(5)));
            em.persist(new AlcoholKeyword(keywords[10], sojus.get(5)));

            em.persist(new AlcoholKeyword(keywords[0], sojus.get(6)));
            em.persist(new AlcoholKeyword(keywords[1], sojus.get(6)));
            em.persist(new AlcoholKeyword(keywords[5], sojus.get(6)));
            em.persist(new AlcoholKeyword(keywords[10], sojus.get(6)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(7)));
            em.persist(new AlcoholKeyword(keywords[10], sojus.get(7)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(8)));
            em.persist(new AlcoholKeyword(keywords[5], sojus.get(8)));
            em.persist(new AlcoholKeyword(keywords[10], sojus.get(8)));

            em.persist(new AlcoholKeyword(keywords[16], sojus.get(9)));
            em.persist(new AlcoholKeyword(keywords[0], sojus.get(9)));
            em.persist(new AlcoholKeyword(keywords[6], sojus.get(9)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(9)));

            em.persist(new AlcoholKeyword(keywords[16], sojus.get(10)));
            em.persist(new AlcoholKeyword(keywords[0], sojus.get(10)));
            em.persist(new AlcoholKeyword(keywords[6], sojus.get(10)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(10)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(11)));
            em.persist(new AlcoholKeyword(keywords[0], sojus.get(11)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(11)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(12)));
            em.persist(new AlcoholKeyword(keywords[6], sojus.get(12)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(12)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(13)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(13)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(14)));
            em.persist(new AlcoholKeyword(keywords[0], sojus.get(14)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(14)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(15)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(15)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(16)));
            em.persist(new AlcoholKeyword(keywords[0], sojus.get(16)));
            em.persist(new AlcoholKeyword(keywords[12], sojus.get(16)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(17)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(17)));

            em.persist(new AlcoholKeyword(keywords[3], sojus.get(18)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(18)));

            em.persist(new AlcoholKeyword(keywords[1], sojus.get(19)));
            em.persist(new AlcoholKeyword(keywords[11], sojus.get(19)));
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

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("GS25")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("GS25")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("CU")));

            em.persist(new Vendor(sojus.get(9), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(9), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(9), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(10), VendorName.valueOf("CU")));
            em.persist(new Vendor(sojus.get(10), VendorName.valueOf("GS25")));
            em.persist(new Vendor(sojus.get(10), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(sojus.get(8), VendorName.valueOf("GS25")));
        }

        private void setBeers(ArrayList<Alcohol> beers) {
            beers.add(new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                    5f, null, null, "산미구엘 브루어리", "필리핀",2L));
            beers.add(new Alcohol(BEER, "tiger.jpg", "타이거 아시안 라거", 2500,
                    5f, null, null, "아시아 퍼시픽 브루어리", "싱가포르",1L));
            beers.add(new Alcohol(BEER, "budweiser.png", "버드와이저", 2500,
                    5f, null, null, "앤하이저부시", "미국",2L));
            beers.add(new Alcohol(BEER, "gompyo.png", "곰표 밀맥주", 2500,
                    4.5f, null, null, "세븐브로이맥주", "대한민국",0L));
            beers.add(new Alcohol(BEER, "hite-extra-cold.png", "하이트 엑스트라 콜드", 2800,
                    4.5f, null, null, "하이트진로", "대한민국",0L));
            beers.add(new Alcohol(BEER, "kozel-premium-lager.png", "코젤 프리미엄 라거", 2500,
                    4.6f, null, null, "필젠스키 프레즈드로이", "체코",0L));
            beers.add(new Alcohol(BEER, "somersby.png", "서머스비(사과)", 4200,
                    4.5f, null, null, "칼스버그 서플라이", "덴마크",2L));
            beers.add(new Alcohol(BEER, "stella-artois.png", "스텔라 아르투아", 3000,
                    5f, null, null, "스텔라 아르투아 브루어리", "벨기에",0L));
            beers.add(new Alcohol(BEER, "guinness-draught.png", "기네스 드래프트", 3000,
                    4.2f, null, null, "디아지오 아일랜드", "아일랜드",0L));
            beers.add(new Alcohol(BEER, "heineken.png", "하이네켄 오리지널", 3900,
                    5f, null, null, "하이네켄 네덜란트 서플라이", "네덜란드",0L));
            beers.add(new Alcohol(BEER, "malpyo-greengrape.png", "말표 청포도", 3500,
                    4f, null, null, "스퀴즈 브루어리", "대한민국",0L));
            beers.add(new Alcohol(BEER, "malpyo-dark.png", "말표 흑맥주", 3500,
                    4.5f, null, null, "스퀴즈 브루어리", "대한민국",0L));
            beers.add(new Alcohol(BEER, "lets.png", "레츠", 1800,
                    4.5f, null, null, "신세계L&B", "대한민국",0L));
            beers.add(new Alcohol(BEER, "cass-fresh.png", "카스 프레쉬", 2700,
                    4.5f, null, null, "오비맥주", "대한민국",0L));
            beers.add(new Alcohol(BEER, "terra.png", "테라", 2500,
                    4.6f, null, null, "하이트진로", "대한민국",0L));
            beers.add(new Alcohol(BEER, "hanmac.png", "한맥", 2700,
                    4.6f, null, null, "오비맥주", "대한민국",0L));
            beers.add(new Alcohol(BEER, "hoegaarden.png", "호가든", 3000,
                    4.9f, null, null, "오비맥주", "벨기에",0L));
            beers.add(new Alcohol(BEER, "kronenbourg-1664-blanc.png", "크로넨버그 1664 블랑", 3200,
                    5f, null, null, "칼스버그 서플라이", "덴마크",0L));
            beers.add(new Alcohol(BEER, "edelweiss-snowfresh.png", "에델바이스 스노우프레쉬", 4000,
                    5f, null, null, "하이네켄 네덜란드 서플라이", "네덜란드",0L));
            beers.add(new Alcohol(BEER, "paulaner-munich-lager.png", "파울라너 뮌헨 라거", 3900,
                    4.9f, null, null, "파울라너 브루어리", "독일",0L));
            beers.add(new Alcohol(BEER, "calsberg.png", "칼스버그", 3000,
                    5f, null, null, "칼스버그 서플라이", "덴마크",0L));
            beers.add(new Alcohol(BEER, "tsingtao.png", "칭따오", 3200,
                    4.7f, null, null, "칭따오 브루어리", "중국",0L));
            beers.add(new Alcohol(BEER, "kozel-dark.png", "코젤 다크", 4000,
                    3.8f, null, null, "필젠스키 프레즈드로이", "체코",0L));
            beers.add(new Alcohol(BEER, "hoegaarden.png", "호가든 포멜로", 3000,
                    3f, null, null, "오비맥주", "벨기에",0L));
        }

        private void persistBeerKeywords(Keyword[] keywords, ArrayList<Alcohol> beers) {
            em.persist(new AlcoholKeyword(keywords[12], beers.get(0))); //산미구엘
            em.persist(new AlcoholKeyword(keywords[5], beers.get(0)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(0)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(1))); //타이거
            em.persist(new AlcoholKeyword(keywords[5], beers.get(1)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(1)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(2))); //버드와이저
            em.persist(new AlcoholKeyword(keywords[1], beers.get(2)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(2)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(3))); //곰표 밀맥주
            em.persist(new AlcoholKeyword(keywords[16], beers.get(3)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(3)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(4))); //하이트
            em.persist(new AlcoholKeyword(keywords[2], beers.get(4)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(4)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(5))); //코젤 라거
            em.persist(new AlcoholKeyword(keywords[2], beers.get(5)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(5)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(6))); //서머스비
            em.persist(new AlcoholKeyword(keywords[16], beers.get(6)));
            em.persist(new AlcoholKeyword(keywords[0], beers.get(6)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(7))); //스텔라
            em.persist(new AlcoholKeyword(keywords[1], beers.get(7)));
            em.persist(new AlcoholKeyword(keywords[7], beers.get(7)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(8))); //기네스
            em.persist(new AlcoholKeyword(keywords[5], beers.get(8)));
            em.persist(new AlcoholKeyword(keywords[9], beers.get(8)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(9))); //하이네켄
            em.persist(new AlcoholKeyword(keywords[2], beers.get(9)));
            em.persist(new AlcoholKeyword(keywords[0], beers.get(9)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(10))); //말표 청포도
            em.persist(new AlcoholKeyword(keywords[16], beers.get(10)));
            em.persist(new AlcoholKeyword(keywords[0], beers.get(10)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(11))); //말표 흑맥주
            em.persist(new AlcoholKeyword(keywords[5], beers.get(11)));
            em.persist(new AlcoholKeyword(keywords[9], beers.get(11)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(12))); //레츠
            em.persist(new AlcoholKeyword(keywords[2], beers.get(12)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(12)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(13))); //카스 프레쉬
            em.persist(new AlcoholKeyword(keywords[2], beers.get(13)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(13)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(14))); //테라
            em.persist(new AlcoholKeyword(keywords[2], beers.get(14)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(14)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(15))); //한맥
            em.persist(new AlcoholKeyword(keywords[2], beers.get(15)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(15)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(16))); //호가든
            em.persist(new AlcoholKeyword(keywords[16], beers.get(16)));
            em.persist(new AlcoholKeyword(keywords[8], beers.get(16)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(17))); //블랑
            em.persist(new AlcoholKeyword(keywords[16], beers.get(17)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(17)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(18))); //에델바이스
            em.persist(new AlcoholKeyword(keywords[9], beers.get(18)));
            em.persist(new AlcoholKeyword(keywords[2], beers.get(18)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(19))); //파울라너
            em.persist(new AlcoholKeyword(keywords[0], beers.get(19)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(19)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(20))); //칼스버그
            em.persist(new AlcoholKeyword(keywords[2], beers.get(20)));
            em.persist(new AlcoholKeyword(keywords[1], beers.get(20)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(21))); //칭따오
            em.persist(new AlcoholKeyword(keywords[2], beers.get(21)));
            em.persist(new AlcoholKeyword(keywords[5], beers.get(21)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(22))); //코젤 다크
            em.persist(new AlcoholKeyword(keywords[5], beers.get(22)));
            em.persist(new AlcoholKeyword(keywords[0], beers.get(22)));
            em.persist(new AlcoholKeyword(keywords[9], beers.get(22)));

            em.persist(new AlcoholKeyword(keywords[12], beers.get(23))); //호가든 포멜로
            em.persist(new AlcoholKeyword(keywords[16], beers.get(23)));
            em.persist(new AlcoholKeyword(keywords[0], beers.get(23)));
        }

        private void persistBeerVendors(ArrayList<Alcohol> beers) {
            for (Alcohol beer : beers) {
                em.persist(new Vendor(beer, VendorName.CU));
                em.persist(new Vendor(beer, VendorName.GS25));
                em.persist(new Vendor(beer, VendorName.SEVEN_ELEVEN));
            }
        }

        private void setWines(ArrayList<Alcohol> wines) {
            wines.add(new Alcohol(WINE, "louis-jadot.png", "루이자도 부르고뉴 샤르도네", 35000,
                    13f, (byte) 1, (byte) 3, "루이 자도", "프랑스 부르고뉴",0L));
            wines.add(new Alcohol(WINE, "michele-le-orme.png", "미켈레 레오르메", 40000,
                    13f, (byte) 0, (byte) 5, "프레스코발디", "이탈리아",0L));
            wines.add(new Alcohol(WINE, "frescobaldi-remole-bianco.png", "프레스코발디 레몰레 비앙코", 29000,
                    12.5f, (byte) 0, (byte) 2, "미켈레 끼아를로", "이탈리아",1L));
            wines.add(new Alcohol(WINE, "tignanello-grappa.png", "티냐넬로그라파", 190000,
                    42f, (byte) 1, (byte) 5, "마르케시 안토리니", "이탈리아",1L));
            wines.add(new Alcohol(WINE, "della-trappola.png", "리카솔리 안티코 페우도 델라 트라폴라 토스카나 IGT", 50000,
                    14f, (byte) 1, (byte) 5, "리카솔리", "이탈리아",1L));
            wines.add(new Alcohol(WINE, "gancia-moscato-dasti.png", "간치아 모스카토 다스티 DO", 20680,
                    7.5f, (byte) 3, (byte) 2, "간치아", "이탈리아",0L));
            wines.add(new Alcohol(WINE, "nederburg-duet-shiraz-pinotage.png", "니더버그듀엣 쉬라즈 & 피노타지", 16280,
                    14.5f, (byte) 1, (byte) 4, "니더버그", "남아프리카공화국",1L));
            wines.add(new Alcohol(WINE, "canti-moscato-dasti.png", "칸티 모스카토 다스티", 22000,
                    7f, (byte) 4, (byte) 2, "칸티", "이탈리아",0L));
            wines.add(new Alcohol(WINE, "sierra-cantabria-rose.png", "시에라 칸따브리아 로제", 30000,
                    13.5f, (byte) 1, (byte) 3, "시에라", "스페인",0L));
            wines.add(new Alcohol(WINE, "brown-brothers-moonstruck.png", "브라운 브라더스 문스트록 모스카토", 16390,
                    5.5f, (byte) 3, (byte) 2, "브라운 브라더스", "호주",0L));
            wines.add(new Alcohol(WINE, "michele-chiarlo-cipressi-nizza.png", "미켈레 끼아를로 치프레스 니짜", 60000,
                    14f, (byte) 1, (byte) 4, "미켈레 끼아를로", "이탈리아",0L));
            wines.add(new Alcohol(WINE, "gato-negro-cabernet.png", "가또 네그로 카베르네", 18000,
                    13f, (byte) 1, (byte) 4, "산 페드로", "칠레",0L));
            wines.add(new Alcohol(WINE, "novibolle-romagna.png", "노비볼레 로마냐 비앙코", 19000,
                    12f, (byte) 1, (byte) 2, "까비로", "이탈리아",0L));
            wines.add(new Alcohol(WINE, "babich.png", "배비치, 블랙 라벨 말보로 피노 누아", 23900,
                    13.5f, (byte) 1, (byte) 4, "배비치", "뉴질랜드 사우스 아일랜드 말보로",0L));
            wines.add(new Alcohol(WINE, "kilikannon.jpg", "킬리카눈 킬러맨즈런 쉬라즈", 25900,
                    15f, (byte) 1, (byte) 4, "킬리카눈", "호주 남호주",0L));
            wines.add(new Alcohol(WINE, "madame.jpg", "마담 드 레인", 72900,
                    13.5f, (byte) 5, (byte) 4, "사또 레인 비뇨", "프랑스 보르도 소테른",0L));
            wines.add(new Alcohol(WINE, "montalto.jpg", "몬탈토 아쿠아렐로 네로다볼라 DOC", 20000,
                    13f, (byte) 2, (byte) 4, "바론 몬탈토", "이탈리아 시칠리아",0L));
            wines.add(new Alcohol(WINE, "the-seasons-vivaldi-spring.png", "더시즌스 비발디 봄", 28000,
                    13.5f, (byte) 1, (byte) 3, "우마니론끼", "이탈리아 마르께",0L));
            wines.add(new Alcohol(WINE, "the-seasons-vivaldi-summer.png", "더시즌스 비발디 여름", 28000,
                    13.5f, (byte) 1, (byte) 3, "우마니론끼", "이탈리아 마르께",0L));
            wines.add(new Alcohol(WINE, "the-seasons-vivaldi-autumn.png", "더시즌스 비발디 가을", 28000,
                    13.5f, (byte) 1, (byte) 3, "우마니론끼", "이탈리아 마르께",0L));
            wines.add(new Alcohol(WINE, "the-seasons-vivaldi-winter.png", "더시즌스 비발디 겨울", 28000,
                    13.5f, (byte) 1, (byte) 3, "우마니론끼", "이탈리아 마르께",0L));
            wines.add(new Alcohol(WINE, "the-seasons-vivaldi-spring.png", "골든블랑 브뤼", 118900,
                    12f, (byte) 1, (byte) 3, "볼레로", "프랑스 샹빠뉴",0L));
            wines.add(new Alcohol(WINE, "Golden-Blanc-Brut-Rose.png", "골든블랑 로제", 145900,
                    12f, (byte) 1, (byte) 3, "볼레로", "프랑스 샹빠뉴",0L));
            wines.add(new Alcohol(WINE, "yellow-tail-shiraz.png", "옐로우테일 쉬라즈", 20000,
                    13.5f, (byte) 1, (byte) 3, "옐로우 테일", "호주 뉴 사우스 웨일즈",0L));
            wines.add(new Alcohol(WINE, "blumoon-moscato.jpg", "아바찌아, 블루문 모스카토", 15000,
                    7f, (byte) 3, (byte) 1, "아바찌아", "이탈리아 피에몬테",0L));
            wines.add(new Alcohol(WINE, "louis-m.jpg", "루이스 엠 마티니, 소노마 카운티", 27900,
                    13f, (byte) 1, (byte) 5, "루이 마티니", "미국 캘리포니아 소노마 카운티",0L));
            wines.add(new Alcohol(WINE, "bread-and-butter.png", "브레드 앤 버터 피노누아", 34500,
                    13.5f, (byte) 1, (byte) 5, "브레드 앤 버터", "미국 캘리포니아 나파 밸리",0L));
            wines.add(new Alcohol(WINE, "joseph-drouhin.png", "조셉 드루앙, 라포레 부르고뉴 블랑", 34900,
                    13.5f, (byte) 1, (byte) 3, "조셉 드루앙", "프랑스",0L));
            wines.add(new Alcohol(WINE, "misty-cove-estate.png", "미스티 코브 에스테이브 소비뇽 블랑", 23900,
                    14f, (byte) 1, (byte) 2, "미스티 코브 와인즈", "뉴질랜드",0L));
            wines.add(new Alcohol(WINE, "sbodegas-lan-reserva.png", "보데가스 란 리제르바", 34000,
                    13f, (byte) 1, (byte) 4, "보데가스 란", "스페인",0L));
        }

        private void persistWineKeywords(Keyword[] keywords, ArrayList<Alcohol> wines) {
            em.persist(new AlcoholKeyword(keywords[7], wines.get(0))); //꽃
            em.persist(new AlcoholKeyword(keywords[16], wines.get(0))); //과일
            em.persist(new AlcoholKeyword(keywords[11], wines.get(0))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[20], wines.get(0))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[16], wines.get(1))); //과일
            em.persist(new AlcoholKeyword(keywords[14], wines.get(1))); //레드
            em.persist(new AlcoholKeyword(keywords[3], wines.get(1))); //무거움
            em.persist(new AlcoholKeyword(keywords[11], wines.get(1))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[20], wines.get(1))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[7], wines.get(2))); //꽃
            em.persist(new AlcoholKeyword(keywords[16], wines.get(2))); //과일
            em.persist(new AlcoholKeyword(keywords[13], wines.get(2))); //화이트
            em.persist(new AlcoholKeyword(keywords[11], wines.get(2))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[20], wines.get(2))); //중간 산도

            em.persist(new AlcoholKeyword(keywords[3], wines.get(3))); //무거움
            em.persist(new AlcoholKeyword(keywords[10], wines.get(3))); //도수 독함
            em.persist(new AlcoholKeyword(keywords[20], wines.get(3))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[16], wines.get(4))); //과일향
            em.persist(new AlcoholKeyword(keywords[3], wines.get(4))); //무거움
            em.persist(new AlcoholKeyword(keywords[15], wines.get(4))); //떫음
            em.persist(new AlcoholKeyword(keywords[11], wines.get(4))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[20], wines.get(4))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[7], wines.get(5))); //꽃
            em.persist(new AlcoholKeyword(keywords[16], wines.get(5))); //과일
            em.persist(new AlcoholKeyword(keywords[11], wines.get(5))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[20], wines.get(5))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[16], wines.get(6))); //과일
            em.persist(new AlcoholKeyword(keywords[3], wines.get(6))); //무거움
            em.persist(new AlcoholKeyword(keywords[11], wines.get(6))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[20], wines.get(6))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[7], wines.get(7))); //꽃
            em.persist(new AlcoholKeyword(keywords[0], wines.get(7))); //달콤함
            em.persist(new AlcoholKeyword(keywords[13], wines.get(7))); //화이트
            em.persist(new AlcoholKeyword(keywords[12], wines.get(7))); //도수 순함
            em.persist(new AlcoholKeyword(keywords[20], wines.get(7))); //낮은 산도

            em.persist(new AlcoholKeyword(keywords[16], wines.get(8))); //과일
            em.persist(new AlcoholKeyword(keywords[11], wines.get(8))); //도수 중간
            em.persist(new AlcoholKeyword(keywords[19], wines.get(8))); //중간 산도
        }

        private void persistWineVendors(ArrayList<Alcohol> wines) {
            em.persist(new Vendor(wines.get(0), VendorName.valueOf("GS25")));

            em.persist(new Vendor(wines.get(1), VendorName.valueOf("CU")));

            em.persist(new Vendor(wines.get(2), VendorName.valueOf("CU")));
            em.persist(new Vendor(wines.get(2), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(wines.get(3), VendorName.valueOf("CU")));

            em.persist(new Vendor(wines.get(4), VendorName.valueOf("CU")));

            em.persist(new Vendor(wines.get(5), VendorName.valueOf("CU")));

            em.persist(new Vendor(wines.get(6), VendorName.valueOf("CU")));

            em.persist(new Vendor(wines.get(7), VendorName.valueOf("CU")));
            em.persist(new Vendor(wines.get(7), VendorName.valueOf("GS25")));

            em.persist(new Vendor(wines.get(8), VendorName.valueOf("CU")));
            em.persist(new Vendor(wines.get(8), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(8), VendorName.valueOf("SEVEN_ELEVEN")));

            em.persist(new Vendor(wines.get(14), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(14), VendorName.valueOf("SEVEN_ELEVEN")));
            em.persist(new Vendor(wines.get(15), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(16), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(17), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(18), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(19), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(20), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(21), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(22), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(23), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(24), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(25), VendorName.valueOf("SEVEN_ELEVEN")));
            em.persist(new Vendor(wines.get(26), VendorName.valueOf("SEVEN_ELEVEN")));
            em.persist(new Vendor(wines.get(27), VendorName.valueOf("SEVEN_ELEVEN")));
            em.persist(new Vendor(wines.get(28), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(28), VendorName.valueOf("SEVEN_ELEVEN")));
            em.persist(new Vendor(wines.get(29), VendorName.valueOf("GS25")));
            em.persist(new Vendor(wines.get(29), VendorName.valueOf("SEVEN_ELEVEN")));
        }

        private void setKeywords(Keyword[] keywords) {
            String[] keywordNames = {"sweet", "clear", "cool", "heavy", "light",
                    "nutty", "fresh", "flower", "bitter", "unique",
                    "strong", "middle", "mild", "white", "red",
                    "astringent", "fruit", "nonAlcoholic", "highAcidity", "middleAcidity",
                    "lowAcidity"
            };

            for (int i = 0; i < keywords.length; i++) {
                keywords[i] = new Keyword(keywordNames[i]);
            }
        }
    }
}