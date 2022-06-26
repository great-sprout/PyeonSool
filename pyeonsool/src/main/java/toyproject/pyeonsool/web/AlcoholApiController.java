package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.service.AlcoholService;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alcohols")
public class AlcoholApiController {
    private final AlcoholService alcoholService;

    @PostMapping("/{alcoholId}/like")
    public ResponseEntity<Void> likeAlcohol(
            @PathVariable Long alcoholId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        alcoholService.likeAlcohol(alcoholId, loginMember.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{alcoholId}/dislike")
    public ResponseEntity<Void> dislikeAlcohol(
            @PathVariable Long alcoholId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        alcoholService.dislikeAlcohol(alcoholId, loginMember.getId());

        return ResponseEntity.ok().build();
    }


}
