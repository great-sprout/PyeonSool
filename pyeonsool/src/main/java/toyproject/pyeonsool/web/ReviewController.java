package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.service.ReviewService;

import java.util.Objects;

import static java.util.Objects.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public String addReview(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            ReviewSaveForm form, RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("alcoholId", form.getAlcoholId());

        // TODO loginMember가 null인 경우 로그인 페이지로 이동하는 인터셉터 구현
        if (isNull(loginMember)) {
            return "redirect:/members/login?redirectURL=alcohols/{alcoholId}";
        }

        reviewService.addReview(loginMember.getId(), form.getAlcoholId(), form.getGrade(), form.getContent());

        return "redirect:/alcohols/{alcoholId}";
    }
}
