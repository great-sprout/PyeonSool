package toyproject.pyeonsool.review.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.review.sevice.ReviewService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    //마이페이지 리뷰 리스트 목록 조회 컨트롤 부분
    @PostMapping("/{reviewId}/edit")
    public String editReview(
            @PathVariable Long reviewId, @RequestParam String requestURI,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            ReviewUpdateForm form, RedirectAttributes redirectAttributes) {

        reviewService.editReview(reviewId,form.getGrade(), form.getContent());

        if (requestURI.equals("members/")) {
            redirectAttributes.addAttribute("nickname",loginMember.getNickname());
            return "redirect:/members/{nickname}"; // nickname 한글 깨짐 처리
        }
        return "redirect:/" + requestURI;
    }
}


