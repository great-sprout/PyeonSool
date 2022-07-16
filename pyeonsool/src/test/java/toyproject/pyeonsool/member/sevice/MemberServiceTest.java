package toyproject.pyeonsool.member.sevice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.pyeonsool.common.exception.form.DuplicateNicknameException;
import toyproject.pyeonsool.common.exception.form.DuplicatePhoneException;
import toyproject.pyeonsool.common.exception.form.DuplicateUserIdException;
import toyproject.pyeonsool.keyword.repository.KeywordRepository;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.mykeyword.repository.MyKeywordRepository;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    KeywordRepository keywordRepository;

    @Mock
    MyKeywordRepository myKeywordRepository;

    @Nested
    class signupTest {
        @Test
        void should_Success_When_Signup() {
            //given
            when(memberRepository.existsByNickname(anyString())).thenReturn(false);
            when(memberRepository.existsByUserId(anyString())).thenReturn(false);
            when(memberRepository.existsByPhoneNumber(anyString())).thenReturn(false);

            //when
            //then
            assertThatNoException().isThrownBy(() -> memberService.signup("nickname", "userId2",
                    "password", "01012345678", null));
        }

        @Test
        void should_ThrowException_When_NicknameIsDuplicate() {
            //given
//            new Member("nickname", "userId", "password", "01012345678");
            when(memberRepository.existsByNickname(anyString())).thenReturn(true);
            //when
            //then
            assertThatThrownBy(() -> memberService.signup("nickname", "userId2", "password",
                    "01012345678", null))
                    .isExactlyInstanceOf(DuplicateNicknameException.class);
        }

        @Test
        void should_ThrowException_When_UserIdIsDuplicate() {
            //given
            when(memberRepository.existsByNickname(anyString())).thenReturn(false);
            when(memberRepository.existsByUserId(anyString())).thenReturn(true);

            //when
            //then
            assertThatThrownBy(() -> memberService.signup("nickname", "userId2", "password",
                    "01012345678", null))
                    .isExactlyInstanceOf(DuplicateUserIdException.class);
        }

        @Test
        void should_ThrowException_When_PhoneNumberIsDuplicate() {
            //given
            when(memberRepository.existsByNickname(anyString())).thenReturn(false);
            when(memberRepository.existsByUserId(anyString())).thenReturn(false);
            when(memberRepository.existsByPhoneNumber(anyString())).thenReturn(true);
            //when
            //then
            assertThatThrownBy(() -> memberService.signup("nickname", "userId2", "password",
                    "01012345678", null))
                    .isExactlyInstanceOf(DuplicatePhoneException.class);
        }
    }
}