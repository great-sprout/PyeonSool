package toyproject.pyeonsool.alcohol.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.pyeonsool.DBConfig;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcoholkeyword.domain.AlcoholKeyword;
import toyproject.pyeonsool.keyword.domain.Keyword;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.preferredalcohol.domain.PreferredAlcohol;
import toyproject.pyeonsool.vendor.domain.Vendor;
import toyproject.pyeonsool.vendor.domain.VendorName;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.alcohol.domain.AlcoholType.*;
import static toyproject.pyeonsool.vendor.domain.VendorName.GS25;

@DataJpaTest
@Import(DBConfig.class)
class AlcoholRepositoryTest {
    @Autowired
    AlcoholRepository alcoholRepository;

    @Autowired
    EntityManager em;

    @Test
    void getAlcoholType() {
        //given
        Alcohol alcohol1 = new Alcohol(BEER, "san-miguel.png", "산", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol2 = new Alcohol(BEER, "san-miguel.png", "산미", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol3 = new Alcohol(BEER, "san-miguel.png", "산미구", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol4 = new Alcohol(BEER, "san-miguel.png", "산미구엘", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol5 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol6 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol7 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol8 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol9 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠1", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
        Alcohol alcohol10 = new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠12", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");

        em.persist(alcohol1);
        em.persist(alcohol2);
        em.persist(alcohol3);
        em.persist(alcohol4);
        em.persist(alcohol5);
        em.persist(alcohol6);
        em.persist(alcohol7);
        em.persist(alcohol8);
        em.persist(alcohol9);
        em.persist(alcohol10);
        int SIZE = 8;
        //when
        Page<Alcohol> alcoholPage = alcoholRepository
                .findAllByType(BEER, PageRequest.of(0, SIZE, Sort.by(Sort.Direction.ASC, "id")));


        //then
        assertThat(alcoholPage.isLast()).isFalse();
        assertThat(alcoholPage.isFirst()).isTrue();
        assertThat(alcoholPage.getTotalElements()).isEqualTo(10);
    }

    @Test
    void getFindAllByTypeTest() {
        //given
        initData();
        List<String> typeKeyword = List.of("cool", "clear");

        //when
        AlcoholSearchConditionDto[] conditions = new AlcoholSearchConditionDto[]{

                new AlcoholSearchConditionDto(BEER, typeKeyword, "리", GS25),
                new AlcoholSearchConditionDto(BEER, typeKeyword, null, GS25),
                new AlcoholSearchConditionDto(BEER, typeKeyword, "리", null),
                new AlcoholSearchConditionDto(BEER, null, "리", GS25),
                new AlcoholSearchConditionDto(BEER, null, null, null),
                new AlcoholSearchConditionDto(BEER, null, null, null)

        };

        ArrayList<Page<Alcohol>> alcoholPages = new ArrayList<>();
        for (AlcoholSearchConditionDto condition : conditions) {
            alcoholPages.add(alcoholRepository.findAllByType(
                    PageRequest.of(0, 8, Sort.by(Sort.Direction.ASC, "likeCount")), condition));
        }

        //then
        int i = 1;
        for (Page<Alcohol> a : alcoholPages) {
            System.out.println(i);

            for (Alcohol s : a) {
                System.out.println("hello = " + s.getName() + " abv : " + s.getAbv() + " price : " + s.getPrice() + " likecount : " + s.getLikeCount());

            }
            i++;
            System.out.println("------------------");
        }
        assertThat(alcoholPages.get(0).getContent().size()).isEqualTo(1);
        assertThat(alcoholPages.get(1).getContent().size()).isEqualTo(7);
        assertThat(alcoholPages.get(2).getContent().size()).isEqualTo(1);
        assertThat(alcoholPages.get(3).getContent().size()).isEqualTo(1);
        assertThat(alcoholPages.get(4).getContent().size()).isEqualTo(8);
        assertThat(alcoholPages.get(5).getContent().size()).isEqualTo(8);
    }

    @Test
    void should_Success_When_RelatedAlcoholsAreObtained() {
        //given
        List<Member> members = List.of(
                new Member("준영이", "chlwnsdud121", "1234", "01012345678"),
                new Member("영준이", "chldudwns121", "1234", "01023456789"),
                new Member("춘향이", "chlcnsgid121", "1234", "01043218765")
        );
        for (Member m : members) {
            em.persist(m);
        }

        List<Keyword> keywords = List.of(
                new Keyword("cool"),
                new Keyword("fresh"),
                new Keyword("middle")
        );
        for (Keyword keyword : keywords) {
            em.persist(keyword);
        }

        List<Alcohol> alcoholList = List.of(
                new Alcohol(SOJU, "jinro.jpg", "진로 이즈 백", 1800, 16.5f,
                        null, null, "하이트 진로(주)", "대한민국", 0L),
                new Alcohol(SOJU, "jamong-chamisul.jpg", "자몽에 이슬", 1900, 13f,
                        null, null, "하이트 진로(주)", "대한민국", 0L),
                new Alcohol(SOJU, "chamisul.png", "참이슬", 1950, 16.5f,
                        null, null, "하이트 진로(주)", "대한민국", 0L),
                new Alcohol(SOJU, "like-first.jpg", "처음처럼", 1950, 16.5f,
                        null, null, "롯데칠성음료(주)", "대한민국", 0L)
        );

        for (Alcohol alcohol : alcoholList) {
            em.persist(alcohol);
        }

        em.persist(new PreferredAlcohol(members.get(0), alcoholList.get(1)));
        em.persist(new PreferredAlcohol(members.get(1), alcoholList.get(1)));
        em.persist(new PreferredAlcohol(members.get(2), alcoholList.get(2)));
        alcoholList.get(1).plusLikeCount();
        alcoholList.get(1).plusLikeCount();
        alcoholList.get(2).plusLikeCount();

        em.persist(new AlcoholKeyword(keywords.get(0), alcoholList.get(0)));
        em.persist(new AlcoholKeyword(keywords.get(1), alcoholList.get(0)));
        em.persist(new AlcoholKeyword(keywords.get(2), alcoholList.get(0)));
        em.persist(new AlcoholKeyword(keywords.get(0), alcoholList.get(1)));
        em.persist(new AlcoholKeyword(keywords.get(1), alcoholList.get(3)));
        em.persist(new AlcoholKeyword(keywords.get(2), alcoholList.get(2)));

        //when
        List<Alcohol> relatedAlcohols = alcoholRepository.findRelatedAlcohols(alcoholList.get(0).getId(), 12);

        //then
        assertThat(relatedAlcohols.size()).isEqualTo(3);
        assertThat(relatedAlcohols).extracting("name")
                .containsExactly("자몽에 이슬", "참이슬", "처음처럼");
        assertThat(relatedAlcohols).isSortedAccordingTo((o1, o2) -> (int) (o2.getLikeCount() - o1.getLikeCount()));
    }


    private void initData() {
        Member[] members = new Member[5];
        setMembers(members);
        for (Member m : members) {
            em.persist(m);
        }

        Keyword[] keywords = new Keyword[21];
        setKeywords(keywords);
        for (Keyword keyword : keywords) {
            em.persist(keyword);
        }
        System.out.println("hi");
        ArrayList<Alcohol> sojus = new ArrayList<>();
        setSojuList(sojus);

        for (Alcohol alcohol : sojus) {
            em.persist(alcohol);
        }

        persistSojuKeywords(keywords, sojus);
        persistSojuVendors(sojus);

        System.out.println("hello");
        ArrayList<Alcohol> beers = new ArrayList<>();
        setBeers(beers);
        for (Alcohol beer : beers) {
            em.persist(beer);
        }
        persistBeerKeywords(keywords, beers);
        persistBeerVendors(beers);

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
    }

    private void setSojuList(ArrayList<Alcohol> sojus) {
        sojus.add(new Alcohol(SOJU, "jinro.jpg",
                "진로 이즈 백", 1800, 16.5f, null, null, "하이트 진로(주)", "대한민국"));
        sojus.add(new Alcohol(SOJU, "jamong-chamisul.jpg",
                "자몽에 이슬", 1900, 13f, null, null, "하이트 진로(주)", "대한민국"));
        sojus.add(new Alcohol(SOJU, "chamisul.png",
                "참이슬", 1950, 16.5f, null, null, "하이트 진로(주)", "대한민국"));
        sojus.add(new Alcohol(SOJU, "like-first.jpg",
                "처음처럼", 1950, 16.5f, null, null, "롯데칠성음료(주)", "대한민국"));
        sojus.add(new Alcohol(SOJU, "hanlla.png",
                "한라산", 1900, 17.5f, null, null, "(주)한라산", "대한민국"));
        sojus.add(new Alcohol(SOJU, "rabbit-white.jpg",
                "토끼 소주 화이트", 38000, 23f, null, null, "농업회사법인 토끼소주(주)", "대한민국"));
        sojus.add(new Alcohol(SOJU, "munbae.jpg",
                "문배술", 10260, 40f, null, null, "문배주양조원", "대한민국"));
        sojus.add(new Alcohol(SOJU, "gosori.png",
                "제주 고소리술", 22800, 29f, null, null, "제주샘영농조합", "대한민국"));
        sojus.add(new Alcohol(SOJU, "hwayo.jpg",
                "화요", 8500, 25f, null, null, "(주)화요", "대한민국"));
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

    private void setBeers(ArrayList<Alcohol> beers) {
        beers.add(new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀", 3L));
        beers.add(new Alcohol(BEER, "tiger.jpg", "타이거 아시안 라거", 2500,
                5f, null, null, "아시아 퍼시픽 브루어리", "싱가포르", 5L));
        beers.add(new Alcohol(BEER, "budweiser.png", "버드와이저", 2500,
                5f, null, null, "앤하이저부시", "미국", 10L));
        beers.add(new Alcohol(BEER, "gompyo.png", "곰표 밀맥주", 2500,
                4.5f, null, null, "세븐브로이맥주", "한국", 1L));
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
            em.persist(new Vendor(beer, GS25));
            em.persist(new Vendor(beer, VendorName.SEVEN_ELEVEN));
        }
    }

    private void setWines(ArrayList<Alcohol> wines) {
        wines.add(new Alcohol(WINE, "louis-jadot.png", "루이자도 부르고뉴 샤르도네", 35000,
                13f, (byte) 1, (byte) 3, "루이 자도", "프랑스 부르고뉴"));
        wines.add(new Alcohol(WINE, "michele-le-orme.png", "미켈레 레오르메", 40000,
                13f, (byte) 0, (byte) 5, "프레스코발디", "이탈리아"));
        wines.add(new Alcohol(WINE, "frescobaldi-remole-bianco.png", "프레스코발디 레몰레 비앙코", 29000,
                12.5f, (byte) 0, (byte) 2, "미켈레 끼아를로", "이탈리아"));
        wines.add(new Alcohol(WINE, "tignanello-grappa.png", "티냐넬로그라파", 190000,
                42f, (byte) 1, (byte) 5, "마르케시 안토리니", "이탈리아"));
        wines.add(new Alcohol(WINE, "della-trappola.png", "리카솔리 안티코 페우도 델라 트라폴라 토스카나 IGT", 50000,
                14f, (byte) 1, (byte) 5, "리카솔리", "이탈리아"));
        wines.add(new Alcohol(WINE, "gancia-moscato-dasti.png", "간치아 모스카토 다스티 DO", 20680,
                7.5f, (byte) 3, (byte) 2, "간치아", "이탈리아"));
        wines.add(new Alcohol(WINE, "nederburg-duet-shiraz-pinotage.png", "니더버그듀엣 쉬라즈 & 피노타지", 16280,
                14.5f, (byte) 1, (byte) 4, "니더버그", "남아프리카공화국"));
        wines.add(new Alcohol(WINE, "canti-moscato-dasti.png", "칸티 모스카토 다스티", 22000,
                7f, (byte) 4, (byte) 2, "칸티", "이탈리아"));
        wines.add(new Alcohol(WINE, "sierra-cantabria-rose.png", "시에라 칸따브리아 로제", 30000,
                13.5f, (byte) 1, (byte) 3, "시에라", "스페인"));
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