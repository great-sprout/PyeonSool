package toyproject.pyeonsool;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.preferredalcohol.repository.PreferredAlcoholCustomRepositoryImpl;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.member.sevice.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AlcoholService alcoholService;
    private final MemberService memberService;


    @GetMapping("/")
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            Model model) {

        //이달의 추천
        model.addAttribute("monthAlcohols", alcoholService.getMonthAlcohols());
        //베스트 Like
        model.addAttribute("sojus", alcoholService.getBestLike(AlcoholType.SOJU, 4));
        model.addAttribute("beers", alcoholService.getBestLike(AlcoholType.BEER, 4));
        model.addAttribute("wines", alcoholService.getBestLike(AlcoholType.WINE, 4));

        //당신의 편술
        if (loginMember == null) {
            model.addAttribute("isLogin", false);
            return "mainPage";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("pyeonsools", alcoholService.getYourAlcohols(loginMember.getId()));

        /*마이 키워드*/
        if (loginMember!=null) {
            List<String> myKeywords = memberService.getMyKeywordsKOR(loginMember.getId());
            model.addAttribute("personalKeywords", myKeywords);
        }

        return "mainPage";
    }
}
