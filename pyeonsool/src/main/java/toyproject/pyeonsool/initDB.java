package toyproject.pyeonsool;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholKeyword;
import toyproject.pyeonsool.domain.Keyword;

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
            keywords[0] = new Keyword("sweet");
            keywords[1] = new Keyword("clear");
            keywords[2] = new Keyword("cool");
            keywords[3] = new Keyword("heavy");
            keywords[4] = new Keyword("light");
            keywords[5] = new Keyword("nutty");
            keywords[6] = new Keyword("fresh");
            keywords[7] = new Keyword("flower");
            keywords[8] = new Keyword("bitter");
            keywords[9] = new Keyword("unique");
            keywords[10] = new Keyword("strong");
            keywords[11] = new Keyword("middle");
            keywords[12] = new Keyword("mild");
            keywords[13] = new Keyword("white");
            keywords[14] = new Keyword("red");
            keywords[15] = new Keyword("astringent");
            keywords[16] = new Keyword("fruit");
            keywords[17] = new Keyword("nonAlcoholic");
            keywords[18] = new Keyword("highAcidity");
            keywords[19] = new Keyword("middleAcidity");
            keywords[20] = new Keyword("lowAcidity");

            em.persist(keywords[0]);
            em.persist(keywords[1]);
            em.persist(keywords[2]);
            em.persist(keywords[3]);
            em.persist(keywords[4]);
            em.persist(keywords[5]);
            em.persist(keywords[6]);
            em.persist(keywords[7]);
            em.persist(keywords[8]);
            em.persist(keywords[9]);
            em.persist(keywords[10]);
            em.persist(keywords[11]);
            em.persist(keywords[12]);
            em.persist(keywords[13]);
            em.persist(keywords[14]);
            em.persist(keywords[15]);
            em.persist(keywords[16]);
            em.persist(keywords[17]);
            em.persist(keywords[18]);
            em.persist(keywords[19]);
            em.persist(keywords[20]);


            ArrayList<Alcohol> alcohols = new ArrayList<>();
            alcohols.add(new Alcohol(WINE, "louis-jadot.png",
                    "루이자도 부르고뉴 샤르도네", 35000, 13f, null, null, null));

            em.persist(alcohols.get(0));

            em.persist(new AlcoholKeyword(keywords[0], alcohols.get(0)));
            em.persist(new AlcoholKeyword(keywords[1], alcohols.get(0)));

            em.flush();
            em.clear();
        }
    }
}
