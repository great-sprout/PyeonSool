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
            em.persist(keywords[0]);
            em.persist(keywords[1]);

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
