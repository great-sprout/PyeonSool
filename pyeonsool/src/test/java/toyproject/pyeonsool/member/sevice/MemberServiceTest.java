package toyproject.pyeonsool.member.sevice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.keyword.repository.KeywordRepository;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.mykeyword.repository.MyKeywordRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
                    .isExactlyInstanceOf(IllegalArgumentException.class);
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
                    .isExactlyInstanceOf(IllegalArgumentException.class);
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
                    .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }
}