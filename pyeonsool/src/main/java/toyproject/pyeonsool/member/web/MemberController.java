package toyproject.pyeonsool.member.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.Pagination;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.common.exception.PyeonSoolException;
import toyproject.pyeonsool.common.exception.form.PyeonSoolFieldException;
import toyproject.pyeonsool.common.exception.form.PyeonSoolFormException;
import toyproject.pyeonsool.member.sevice.MemberService;
import toyproject.pyeonsool.review.sevice.ReviewImagePathDto;
import toyproject.pyeonsool.review.sevice.ReviewService;
import toyproject.pyeonsool.review.web.ReviewUpdateForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AlcoholService alcoholService;
    private final ReviewService reviewService;

    //내 Like 리스트 컨트롤러
    @GetMapping("/{nickname}")
    //로그인 세션값이 필요하다
    public String getMyPage(
            @PathVariable String nickname,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            HttpServletResponse response,
            @PageableDefault(sort = "id", size = 5, direction = Sort.Direction.DESC) Pageable pageable,
            Model model) throws IOException {

        if (!memberService.getMemberId(nickname).equals(loginMember.getId())) {
            response.sendError(500, "잘못된 접근입니다.");
        }

        // TODO loginMember null값 검증

        model.addAttribute("myLikeList", alcoholService.getAlcoholImages(loginMember.getId()));
        Page<ReviewImagePathDto> myReviewPage = reviewService.getReviewImagePathPage(loginMember.getId(), pageable);
        model.addAttribute("MyReviewPagination", Pagination.of(myReviewPage, 5));
        model.addAttribute("myReviewList", myReviewPage.getContent());
        model.addAttribute("myReviewUpdateForm", new ReviewUpdateForm());
        //model.addAttribute("reviewSaveForm", new ReviewSaveForm(alcoholId));

        /*마이 키워드*/
        if (loginMember != null) {
            List<String> myKeywords = memberService.getMyKeywordsKOR(loginMember.getId());
            model.addAttribute("personalKeywords", myKeywords);
        }

        return "myPage";
    }


    @GetMapping("/login")
    public String getSignInPage(LoginForm loginForm) {
        return "signIn";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request, BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            try {
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionConst.LOGIN_MEMBER,
                        memberService.findLoginMember(loginForm.getUserId(), loginForm.getPassword()));
            } catch (PyeonSoolFieldException e) { // PyeonSoolFormException의 자식 클래스로, service에서 던진 에외로 rejectValue가 가능해짐
                bindingResult.rejectValue(e.getField(), e.getErrorCode(), e.getMessage());
            } catch (PyeonSoolFormException e) { // PyeonSoolException의 자식 클래스로, 글로벌 에러 잡기용. 다중 catch에서는 상위 클래스가 나중에 와야함
                bindingResult.reject(e.getErrorCode(), e.getMessage());
            }
        }

        if (bindingResult.hasErrors()) {
            return "signIn";
        }

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (nonNull(session)) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/add")
    public String getSignupPage(MemberSaveForm memberSaveForm) {
        return "signUp";
    }

    @PostMapping("/add")
    public String signup(@Valid MemberSaveForm memberSaveForm, BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors()) {
            try {
                memberService.signup(memberSaveForm.getNickname(), memberSaveForm.getUserId(),
                        memberSaveForm.getPassword(), memberSaveForm.getPhoneNumber(), memberSaveForm.getKeywords());
            } catch (Exception e) {
                bindingResult.reject("globalError", e.getMessage());//?
            }
        }

        if (bindingResult.hasErrors()) {
            return "signUp";
        }

        return "redirect:/members/login";
    }
}
