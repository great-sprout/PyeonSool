package toyproject.pyeonsool.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void login() {

    }
}
