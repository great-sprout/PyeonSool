package toyproject.pyeonsool.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/{memberId}")
    public String getMyPage(@PathVariable Long memberId) {
        return "myPage";
    }

    @GetMapping("/login")
    public String getSignInPage(LoginForm loginForm) {
        return "signIn";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm, @RequestParam(defaultValue = "/") String redirectURL) {

        return "redirect:/" + redirectURL;
    }
}
