package toyproject.pyeonsool.common.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.pyeonsool.common.exception.api.PyeonSoolApiException;
import toyproject.pyeonsool.common.exception.api.httpstatus.BadRequestException;
import toyproject.pyeonsool.common.exception.api.httpstatus.ForbiddenException;
import toyproject.pyeonsool.common.exception.api.httpstatus.UnauthorizedException;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionType {
    // 요청 파라미터 관련 예외
    REQUIRED_ALCOHOL_ID(new BadRequestException("술 고유 번호는 필수입니다.")),
    REQUIRED_GRADE(new BadRequestException("평점은 필수입니다.")),
    LIMITED_RANGE_GRADE(new BadRequestException("평점은 1 ~ 5 사이로 선택하세요.")),
    NON_BLANK_REVIEW(new BadRequestException("리뷰는 공백일 수 없습니다.")),
    MAX_LENGTH_REVIEW(new BadRequestException("리뷰는 300자 이내로 작성하세요.")),
    REQUIRED_REVIEW_ID(new BadRequestException("리뷰 고유 번호는 필수입니다.")),

    // 로그인 관련 예외
    MUST_LOGIN(new UnauthorizedException("로그인 후 이용해주세요")),

    // 기타 예외
    NOT_EXIST_ALCOHOL(new BadRequestException("존재하지 않는 술입니다.")),
    NOT_EXIST_MEMBER(new BadRequestException("존재하지 않는 회원입니다.")),
    NOT_EXIST_REVIEW(new BadRequestException("존재하지 않는 리뷰입니다.")),
    FORBIDDEN_REVIEW(new ForbiddenException("해당 리뷰에 대한 접근 권한이 없습니다."));


    private final PyeonSoolApiException exception;
}
