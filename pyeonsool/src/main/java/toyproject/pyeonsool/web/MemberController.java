package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.service.AlcoholService;
import toyproject.pyeonsool.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Objects.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AlcoholService alcoholService;

    //내 Like 리스트 컨트롤러
    @GetMapping("/{nickname}")
    //로그인 세션값이 필요하다
    public String getMyPage(
            @PathVariable String nickname,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember, HttpServletRequest request,
            Model model) {
        // TODO loginMember null값 검증
        model.addAttribute("myLikeList", alcoholService.getAlcoholImages(loginMember.getId()));
        return "myPage";
    }


    @GetMapping("/login")
    public String getSignInPage(LoginForm loginForm) {
        return "signIn";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm, @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        LoginMember loginMember = memberService.findLoginMember(loginForm.getUserId(), loginForm.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

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
    public String signup(MemberSaveForm memberSaveForm) {
        memberService.signup(memberSaveForm.getNickname(), memberSaveForm.getUserId(),
                memberSaveForm.getPassword(), memberSaveForm.getPhoneNumber(), memberSaveForm.getKeywords());

        return "redirect:/";
    }
}
