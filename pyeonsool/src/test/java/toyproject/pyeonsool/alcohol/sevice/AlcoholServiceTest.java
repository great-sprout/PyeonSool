package toyproject.pyeonsool.alcohol.sevice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.pyeonsool.alcohol.repository.AlcoholRepository;
import toyproject.pyeonsool.alcoholkeyword.repository.AlcoholKeywordRepository;
import toyproject.pyeonsool.common.FileManager;
import toyproject.pyeonsool.common.exception.api.httpstatus.BadRequestException;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.preferredalcohol.repository.PreferredAlcoholRepository;
import toyproject.pyeonsool.review.repository.ReviewRepository;
import toyproject.pyeonsool.vendor.repository.VendorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static toyproject.pyeonsool.domain.VendorName.*;

@ExtendWith(MockitoExtension.class)
class AlcoholServiceTest {

    @InjectMocks
    AlcoholService alcoholService;

    @Mock
    AlcoholRepository alcoholRepository;

    @Mock
    PreferredAlcoholRepository preferredAlcoholRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AlcoholKeywordRepository alcoholKeywordRepository;

    @Mock
    VendorRepository vendorRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    FileManager fileManager;

    @Nested
    class getAlcoholDetailsTest {
        @Test
        void should_Success_When_AlcoholDetailsIsObtained() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");

            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName()))
                    .thenReturn("/alcohols/test.jpg");
            when(reviewRepository.getReviewGrades(anyLong())).thenReturn(List.of((byte) 3, (byte) 4, (byte) 4));
            when(alcoholKeywordRepository.getAlcoholKeywords(anyLong()))
                    .thenReturn(List.of("sweet", "cool", "clear", "heavy", "light", "nutty", "fresh",
                            "flower", "bitter", "unique", "strong", "middle", "mild", "white", "red", "astringent",
                            "fruit", "nonAlcoholic", "highAcidity", "middleAcidity", "lowAcidity"));
            when(vendorRepository.getAlcoholVendors(anyLong()))
                    .thenReturn(List.of(CU.name(), GS25.name(), SEVEN_ELEVEN.name()));
            when(preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol))
                    .thenReturn(true);

            //when
            AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(1L, 2L);

            //then
            assertThat(alcoholDetails.getGrade()).isEqualTo("3.7");
            assertThat(alcoholDetails.getKeywords())
                    .containsOnly("달콤함", "깔끔함", "청량함", "무거움", "가벼움", "고소함", "상큼함", "꽃향",
                            "쌉싸름함", "독특함", "도수 독함", "도수 중간", "도수 순함", "화이트", "레드", "떫음", "과일향",
                            "무알콜", "높은 산도", "중간 산도", "낮은 산도");
            assertThat(alcoholDetails.isLikeCurrentAlcohol()).isEqualTo(true);
        }

        @Test
        void should_Success_When_notLogin() {
            //given
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName()))
                    .thenReturn("/alcohols/test.jpg");
            when(reviewRepository.getReviewGrades(anyLong())).thenReturn(List.of());
            when(alcoholKeywordRepository.getAlcoholKeywords(anyLong()))
                    .thenReturn(List.of());
            when(vendorRepository.getAlcoholVendors(anyLong()))
                    .thenReturn(List.of());

            //when
            AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(1L, null);

            //then
            assertThat(alcoholDetails.getGrade()).isEqualTo("-");
            assertThat(alcoholDetails.getKeywords()).isEmpty();
            assertThat(alcoholDetails.isLikeCurrentAlcohol()).isEqualTo(false);
        }

        @Test
        void should_ThrowException_When_AlcoholDoNotExist() {
            //given
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> alcoholService.getAlcoholDetails(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
            when(fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName()))
                    .thenReturn("/alcohols/test.jpg");
            when(reviewRepository.getReviewGrades(anyLong())).thenReturn(List.of());
            when(alcoholKeywordRepository.getAlcoholKeywords(anyLong()))
                    .thenReturn(List.of());
            when(vendorRepository.getAlcoholVendors(anyLong()))
                    .thenReturn(List.of());

            //when
            //then
            assertThatThrownBy(() -> alcoholService.getAlcoholDetails(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Test
    void should_NoException_When_relatedAlcoholsAreObtained() {
        //given
        when(alcoholRepository.findRelatedAlcohols(anyLong(), anyInt()))
                .thenReturn(List.of());

        //when
        //then
        assertThatNoException().isThrownBy(() -> alcoholService.getRelatedAlcohols(1L));
    }

    @Nested
    class likeAlcoholTest {
        @Test
        void should_Success_When_AlcoholIsLiked() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(preferredAlcoholRepository.countByMemberId(anyLong())).thenReturn(0L);
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(preferredAlcoholRepository.save(any())).thenReturn(new PreferredAlcohol(member, alcohol));

            //when
            //then
            assertThatNoException().isThrownBy(() -> alcoholService.likeAlcohol(1L, 2L));
            assertThat(alcohol.getLikeCount()).isEqualTo(1);
        }

        @Test
        void should_ThrowException_When_PreferredAlcoholsCountGreaterThan12() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국", 12L);

            when(preferredAlcoholRepository.countByMemberId(anyLong())).thenReturn(12L);

            //when
            //then
            assertThatThrownBy(() -> alcoholService.likeAlcohol(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_AlcoholDoNotExist() {
            //given
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> alcoholService.likeAlcohol(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> alcoholService.likeAlcohol(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    class dislikeAlcoholTest {
        @Test
        void should_Success_When_PreferredAlcoholExists() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국", 1L);

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol))
                    .thenReturn(true);

            //when
            //then
            assertThatNoException().isThrownBy(() -> alcoholService.dislikeAlcohol(1L, 2L));
            assertThat(alcohol.getLikeCount()).isEqualTo(0);
        }

        @Test
        void should_Success_When_PreferredAlcoholDoNotExist() {
            //given
            Member member = new Member("nickname", "userId", "password", "01012345678");
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
            when(preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol))
                    .thenReturn(false);

            //when
            //then
            assertThatNoException().isThrownBy(() -> alcoholService.dislikeAlcohol(1L, 2L));
            assertThat(alcohol.getLikeCount()).isEqualTo(0);
        }

        @Test
        void should_ThrowException_When_AlcoholDoNotExist() {
            //given
            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> alcoholService.dislikeAlcohol(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }

        @Test
        void should_ThrowException_When_MemberDoNotExist() {
            //given
            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");

            when(alcoholRepository.findById(anyLong())).thenReturn(Optional.of(alcohol));
            when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> alcoholService.dislikeAlcohol(1L, 2L))
                    .isExactlyInstanceOf(BadRequestException.class);
        }
    }
}