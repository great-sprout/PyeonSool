package toyproject.pyeonsool.keyword.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.member.sevice.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class KeywordController {

    private final MemberService memberService;

    @PostMapping("/keywords/edit")
    public String editKeyword(
            @RequestParam List<String> keywords,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        //키워드 갯수 검증
        if (keywords.size() != 3) {
            return "redirect:/";
        }

        memberService.editMemberKeywords(keywords, loginMember.getId());

        return "redirect:/";
    }
}
