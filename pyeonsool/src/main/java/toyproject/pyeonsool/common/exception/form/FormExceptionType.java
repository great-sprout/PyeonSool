package toyproject.pyeonsool.common.exception.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormExceptionType {
    INCORRECT_PASSWORD(new IncorrectPasswordException("잘못된 비밀번호입니다.", "incorrect", "password")),
    INCORRECT_USER_ID(new IncorrectUserIdException(
            "입력한 아이디를 사용하는 계정을 찾을 수 없습니다.", "incorrect", "userId")),
    DUPLICATE_USER_ID(new DuplicateUserIdException("이미 등록된 아이디입니다.", "duplicate", "userId")),

    DUPLICATE_NICKNAME(new DuplicateNicknameException("이미 등록된 닉네임 입니다.", "duplicate", "nickname")),

    DUPLICATE_PHONE_NUMBER(new DuplicatePhoneException("이미 등록된 핸드폰 번호입니다.", "duplicate", "phoneNumber"));


    private final PyeonSoolFormException exception;
}
