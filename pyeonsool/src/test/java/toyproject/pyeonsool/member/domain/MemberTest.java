package toyproject.pyeonsool.member.domain;

import org.junit.jupiter.api.Test;
import toyproject.pyeonsool.common.exception.form.IncorrectPasswordException;
import toyproject.pyeonsool.domain.Member;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void should_Success_When_CorrectPassword() {
        //given
        String password = "password";
        Member member = new Member("nickname", "userId", password, "01012345678");

        //when
        //then
        assertThatNoException().isThrownBy(() -> member.validatePassword(password));
    }

    @Test
    void should_ThrowException_When_IncorrectPassword() {
        //given
        Member member = new Member("nickname", "userId", "password", "01012345678");

        //when
        //then
        assertThatThrownBy(() -> member.validatePassword("wrongPassword"))
                .isExactlyInstanceOf(IncorrectPasswordException.class);
    }
}