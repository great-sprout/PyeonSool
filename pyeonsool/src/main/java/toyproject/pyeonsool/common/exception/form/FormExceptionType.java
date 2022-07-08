package toyproject.pyeonsool.common.exception.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormExceptionType {
    INCORRECT_PASSWORD(new IncorrectPasswordException("잘못된 비밀번호입니다.", "incorrect", "password")),
    INCORRECT_USER_ID(new IncorrectUserIdException(
            "입력한 아이디를 사용하는 계정을 찾을 수 없습니다.", "incorrect", "userId"));

    private final PyeonSoolFormException exception;
}
