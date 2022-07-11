package toyproject.pyeonsool.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlcoholTest {

    @Test
    void should_Success_When_LikeCountIncreases() {
        //given
        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");

        //when
        alcohol.plusLikeCount();

        //then
        Assertions.assertThat(alcohol.getLikeCount()).isEqualTo(1);
    }

    @Test
    void should_Success_When_LikeCountDecreases() {
        //given
        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국", 1L);

        //when
        alcohol.minusLikeCount();

        //then
        Assertions.assertThat(alcohol.getLikeCount()).isEqualTo(0);
    }

    @Test
    void should_noChange_When_ZeroLikeCountDecreases() {
        //given
        Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");

        //when
        alcohol.minusLikeCount();

        //then
        Assertions.assertThat(alcohol.getLikeCount()).isEqualTo(0);
    }
}