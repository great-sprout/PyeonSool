package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
        reviewService.addReview(loginMember.getId(), form.getAlcoholId(), form.getGrade(), form.getContent());

        return "redirect:/alcohols/{alcoholId}";
    }

    //마이페이지 리뷰 리스트 목록 조회 컨트롤 부분
    @PostMapping("/{reviewId}/edit")
    public String editReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            ReviewUpdateForm form,RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("nickname",loginMember.getNickname());
        reviewService.editReview(reviewId,form.getGrade(), form.getContent());

        return "redirect:/members/{nickname}";
    }
}


