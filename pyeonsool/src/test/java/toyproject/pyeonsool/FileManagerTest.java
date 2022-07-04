package toyproject.pyeonsool;

import org.junit.jupiter.api.Test;
import toyproject.pyeonsool.common.FileManager;

import static org.assertj.core.api.Assertions.assertThat;
import static toyproject.pyeonsool.domain.AlcoholType.*;

class FileManagerTest {
    FileManager fileManager = new FileManager();

    @Test
    void getAlcoholImagePath() {
        //given
        //when
        String wineImagePath = fileManager.getAlcoholImagePath(WINE, "wine.png");
        String sojuImagePath = fileManager.getAlcoholImagePath(SOJU, "soju.png");
        String beerImagePath = fileManager.getAlcoholImagePath(BEER, "beer.png");

        //then
        assertThat(wineImagePath).isEqualTo("/image/alcohol/wine/wine.png");
        assertThat(sojuImagePath).isEqualTo("/image/alcohol/soju/soju.png");
        assertThat(beerImagePath).isEqualTo("/image/alcohol/beer/beer.png");
    }
}