package toyproject.pyeonsool.common.exception.api.httpstatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.common.exception.api.PyeonSoolApiException;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionType {
    REQUIRED_ALCOHOL_ID(new BadRequestException("술 고유 번호는 필수입니다.")),
    REQUIRED_GRADE(new BadRequestException("평점은 필수입니다.")),
    NON_BLANK_REVIEW(new BadRequestException("리뷰는 공백일 수 없습니다.")),
    MAX_LENGTH_REVIEW(new BadRequestException("리뷰는 300자 이내로 작성하세요.")),

    MUST_LOGIN(new UnauthorizedException("로그인 후 이용해주세요"));


    private final PyeonSoolApiException exception;
}
