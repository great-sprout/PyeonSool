package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.service.AlcoholService;

import java.util.Objects;

import static java.util.Objects.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alcohols")
public class AlcoholApiController {
    private final AlcoholService alcoholService;

    @PostMapping("/{alcoholId}/like")
    public void likeAlcohol(
            @PathVariable Long alcoholId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO 예외를 처리할 advice 클래스 생성
            throw new RuntimeException("로그인 상태가 아닙니다.");
        }
        alcoholService.likeAlcohol(alcoholId, loginMember.getId());
    }
}
