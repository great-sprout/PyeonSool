package toyproject.pyeonsool.review.sevice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import toyproject.pyeonsool.alcohol.repository.AlcoholRepository;
import toyproject.pyeonsool.common.exception.api.httpstatus.BadRequestException;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.recommendedreview.repository.RecommendedReviewRepository;
import toyproject.pyeonsool.review.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    AlcoholRepository alcoholRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    RecommendedReviewRepository recommendedReviewRepository;

    @Nested
    class GetReviewPageTest {
        @Test
        void should_Success_When_MyRecommendStatusIsDISLIKE() {
            //given
            Member member = createMember();
            Alcohol alcohol = createAlcohol();
            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰",
                    0L, 1L);

            PageRequest pageable = PageRequest.of(0, 1);

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(reviewRepository.findReviewsByAlcohol(alcohol, pageable))
                    .thenReturn(new PageImpl<>(List.of(review)));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(recommendedReviewRepository.findByMemberAndReview(member, review))
                    .thenReturn(Optional.of(new RecommendedReview(member, review, RecommendStatus.DISLIKE)));

            //when
            ReviewDto reviewDto = reviewService
                    .getReviewPage(pageable, 1L, 2L)
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.DISLIKE);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(1);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_Success_When_MyRecommendStatusIsLIKE() {
            //given
            Member member = createMember();
            Alcohol alcohol = createAlcohol();
            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰",
                    1L, 0L);

            PageRequest pageable = PageRequest.of(0, 1);

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(reviewRepository.findReviewsByAlcohol(alcohol, pageable))
                    .thenReturn(new PageImpl<>(List.of(review)));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(recommendedReviewRepository.findByMemberAndReview(member, review))
                    .thenReturn(Optional.of(new RecommendedReview(member, review, RecommendStatus.LIKE)));

            //when
            ReviewDto reviewDto = reviewService
                    .getReviewPage(pageable, 1L, 2L)
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.LIKE);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(1);
        }

        @Test
        void should_Success_When_MemberIdIsNull() {
            //given
            Member member = createMember();
            Alcohol alcohol = createAlcohol();
            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰",
                    0L, 0L);

            PageRequest pageable = PageRequest.of(0, 1);

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(reviewRepository.findReviewsByAlcohol(alcohol, pageable))
                    .thenReturn(new PageImpl<>(List.of(review)));

            //when
            ReviewDto reviewDto = reviewService
                    .getReviewPage(pageable, 1L, null)
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.NORMAL);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_Success_When_MemberDoNotExist() {
            //given
            Member member = createMember();
            Alcohol alcohol = createAlcohol();
            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰",
                    0L, 0L);

            PageRequest pageable = PageRequest.of(0, 1);

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(reviewRepository.findReviewsByAlcohol(alcohol, pageable))
                    .thenReturn(new PageImpl<>(List.of(review)));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            ReviewDto reviewDto = reviewService
                    .getReviewPage(pageable, 1L, 2L)
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.NORMAL);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_Success_When_RecommendedReviewDoNotExist() {
            //given
            Member member = createMember();
            Alcohol alcohol = createAlcohol();
            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰",
                    0L, 0L);

            PageRequest pageable = PageRequest.of(0, 1);

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(reviewRepository.findReviewsByAlcohol(alcohol, pageable))
                    .thenReturn(new PageImpl<>(List.of(review)));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(recommendedReviewRepository.findByMemberAndReview(member, review))
                    .thenReturn(Optional.empty());

            //when
            ReviewDto reviewDto = reviewService
                    .getReviewPage(pageable, 1L, 2L)
                    .getContent().get(0);

            //then
            assertThat(reviewDto.getMyRecommendStatus()).isEqualTo(RecommendStatus.NORMAL);
            assertThat(reviewDto.getNotRecommendCount()).isEqualTo(0);
            assertThat(reviewDto.getRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_ThrowException_When_AlcoholDoNotExist() {
            //given
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService
                    .getReviewPage(PageRequest.of(0, 1), 1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    private Alcohol createAlcohol() {
        return new Alcohol(BEER, "san-miguel.png", "산미구엘 페일필젠", 3000,
                5f, null, null, "산미구엘 브루어리", "필리핀");
    }

    private Member createMember() {
        return new Member("준영이", "chlwnsdud121", "1234", "01012345678");
    }

    @Nested
    class AddReviewTest {
        @Test
        void should_Success_When_AlcoholIsLiked() {
            //given
            Member member = createMember();
            Alcohol alcohol = createAlcohol();
            Review review = new Review(member, alcohol, (byte) (3), "테스트 리뷰",
                    0L, 1L);
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(reviewRepository.save(any())).thenReturn(review);

            //when
            //then
            assertThatNoException().isThrownBy(() ->
                    reviewService.addReview(1L, 2L, (byte) 3, "테스트 리뷰"));
        }

        @Test
        void should_ThrowException_When_AlcoholDoNotExist() {
            //given
            Member member = createMember();

            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.addReview(1L, 2L, (byte) 3, "테스트 리뷰"))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.addReview(1L, 2L, (byte) 3, "테스트 리뷰"))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    class EditReviewTest {
        @Test
        void should_ThrowException_When_ReviewDoNotExist() {
            //given
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.editReview(1L, 2L, (byte) 1, "리뷰 수정"))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    class DeleteReviewTest {
        @Test
        void should_Success_When_ReviewExists() {
            //given
            Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 1L);
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));

            //when
            //then
            assertThatNoException().isThrownBy(() -> reviewService.deleteReview(1L));
        }

        @Test
        void should_Success_When_ReviewDoNotExist() {
            //given
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatNoException().isThrownBy(() -> reviewService.deleteReview(1L));
        }
    }

    @Nested
    class RecommendReviewTest {
        @Test
        void should_Success_When_ReviewWasNotRecommended() {
            //given
            Member member = createMember();
            Review review = new Review(member, createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 0L);
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(recommendedReviewRepository.findByMemberAndReview(member, review))
                    .thenReturn(Optional.empty());

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> reviewService.recommendReview(1L, 2L));
            assertThat(review.getRecommendCount()).isEqualTo(1);
            assertThat(review.getNotRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_ThrowException_When_ReviewDoNotExist() {
            //given
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.recommendReview(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 0L);
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.recommendReview(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    class NotRecommendReviewTest {
        @Test
        void should_Success_When_ReviewWasNotRecommended() {
            //given
            Member member = createMember();
            Review review = new Review(member, createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 0L);
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(recommendedReviewRepository.findByMemberAndReview(member, review))
                    .thenReturn(Optional.empty());

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> reviewService.notRecommendReview(1L, 2L));
            assertThat(review.getRecommendCount()).isEqualTo(0);
            assertThat(review.getNotRecommendCount()).isEqualTo(1);
        }

        @Test
        void should_ThrowException_When_ReviewDoNotExist() {
            //given
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.notRecommendReview(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 0L);
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.notRecommendReview(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    class CancelRecommendationTest {
        @Test
        void should_Success_When_RecommendStatusIsLIKE() {
            //given
            Member member = createMember();
            Review review = new Review(member, createAlcohol(), (byte) (3), "테스트 리뷰",
                    1L, 0L);

            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(recommendedReviewRepository.findByMemberAndReviewAndStatus(member, review, RecommendStatus.LIKE))
                    .thenReturn(Optional.of(new RecommendedReview(member, review, RecommendStatus.LIKE)));

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> reviewService.cancelRecommendation(1L, 2L, RecommendStatus.LIKE));
            assertThat(review.getRecommendCount()).isEqualTo(0);
            assertThat(review.getNotRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_Success_When_RecommendStatusIsDISLIKE() {
            //given
            Member member = createMember();
            Review review = new Review(member, createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 1L);

            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(recommendedReviewRepository.findByMemberAndReviewAndStatus(member, review, RecommendStatus.DISLIKE))
                    .thenReturn(Optional.of(new RecommendedReview(member, review, RecommendStatus.DISLIKE)));

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> reviewService.cancelRecommendation(1L, 2L, RecommendStatus.DISLIKE));
            assertThat(review.getRecommendCount()).isEqualTo(0);
            assertThat(review.getNotRecommendCount()).isEqualTo(0);
        }

        @Test
        void should_Success_When_RecommendStatusDoNotExist() {
            //given
            Member member = createMember();
            Review review = new Review(member, createAlcohol(), (byte) (3), "테스트 리뷰",
                    1L, 1L);

            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(recommendedReviewRepository.findByMemberAndReviewAndStatus(member, review, RecommendStatus.LIKE))
                    .thenReturn(Optional.empty());

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> reviewService.cancelRecommendation(1L, 2L, RecommendStatus.LIKE));
            assertThat(review.getRecommendCount()).isEqualTo(1);
            assertThat(review.getNotRecommendCount()).isEqualTo(1);
        }

        @Test
        void should_ThrowException_When_ReviewDoNotExist() {
            //given
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.notRecommendReview(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            Review review = new Review(createMember(), createAlcohol(), (byte) (3), "테스트 리뷰",
                    0L, 0L);
            when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> reviewService.notRecommendReview(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }
}