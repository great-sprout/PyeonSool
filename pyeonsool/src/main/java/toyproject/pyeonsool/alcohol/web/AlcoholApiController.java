package toyproject.pyeonsool.alcohol.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;

import static java.util.Objects.isNull;
import static toyproject.pyeonsool.common.exception.api.ApiExceptionType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alcohols")
public class AlcoholApiController {
    private final AlcoholService alcoholService;

    @PostMapping("/{alcoholId}/like")
    public ResponseEntity<Void> likeAlcohol(
            @PathVariable Long alcoholId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateAlcoholId(alcoholId);

        alcoholService.likeAlcohol(alcoholId, loginMember.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{alcoholId}/dislike")
    public ResponseEntity<Void> dislikeAlcohol(
            @PathVariable Long alcoholId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateAlcoholId(alcoholId);

        alcoholService.dislikeAlcohol(alcoholId, loginMember.getId());

        return ResponseEntity.ok().build();
    }

    private void validateLogin(LoginMember loginMember) {
        if (isNull(loginMember)) {
            throw MUST_LOGIN.getException();
        }
    }

    private void validateAlcoholId(Long alcoholId) {
        if (alcoholId < 0) {
            throw INVALID_ALCOHOL_ID.getException();
        }
    }
}
