package toyproject.pyeonsool.review.domain;

import org.junit.jupiter.api.Test;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {
    @Test
    void should_Success_When_RecommendCountIncreases() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 0L);

        //when
        review.plusRecommendCount();

        //then
        assertThat(review.getRecommendCount()).isEqualTo(1);
    }

    @Test
    void should_Success_When_RecommendCountDecreases() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                1L, 0L);

        //when
        review.minusRecommendCount();

        //then
        assertThat(review.getRecommendCount()).isEqualTo(0);
    }

    @Test
    void should_noChange_When_ZeroRecommendCountDecreases() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 0L);

        //when
        review.minusRecommendCount();

        //then
        assertThat(review.getRecommendCount()).isEqualTo(0);
    }

    @Test
    void should_Success_When_NotRecommendCountIncreases() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 0L);

        //when
        review.plusNotRecommendCount();

        //then
        assertThat(review.getNotRecommendCount()).isEqualTo(1);
    }

    @Test
    void should_Success_When_NotRecommendCountDecreases() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 1L);

        //when
        review.minusNotRecommendCount();

        //then
        assertThat(review.getNotRecommendCount()).isEqualTo(0);
    }

    @Test
    void should_noChange_When_ZeroNotRecommendCountDecreases() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 0L);

        //when
        review.minusNotRecommendCount();

        //then
        assertThat(review.getNotRecommendCount()).isEqualTo(0);
    }

    @Test
    void should_Success_When_ContentIsChanged() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 0L);

        //when
        review.changeContent("수정 리뷰");

        //then
        assertThat(review.getContent()).isEqualTo("수정 리뷰");
    }

    @Test
    void should_Success_When_GradeIsChanged() {
        //given
        Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                0L, 0L);

        //when
        review.changeGrade((byte) 1);

        //then
        assertThat(review.getGrade()).isEqualTo((byte) 1);
    }

    private Alcohol createAlcohol() {
        return new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                (byte) 3, (byte) 2, "우리집", "대한민국");
    }

    private Member createMember() {
        return new Member("nickname", "userId", "password", "01012345678");
    }
}