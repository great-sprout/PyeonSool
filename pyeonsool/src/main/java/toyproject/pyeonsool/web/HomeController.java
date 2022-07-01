package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.repository.PreferredAlcoholCustomRepositoryImpl;
import toyproject.pyeonsool.service.AlcoholService;
import toyproject.pyeonsool.service.MainPageDto;
import toyproject.pyeonsool.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AlcoholService alcoholService;
    private final MemberService memberService;
    private final PreferredAlcoholCustomRepositoryImpl preferAlcohol;


    @GetMapping("/")
    //로그인 세션값 필요함 -> 당신의 편술 블럭처리
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            Model model) {

        model.addAttribute("sojus", alcoholService.getBestLike(AlcoholType.SOJU, 4));
        model.addAttribute("beers", alcoholService.getBestLike(AlcoholType.BEER, 4));
        model.addAttribute("wines", alcoholService.getBestLike(AlcoholType.WINE, 4));

        //블럭처리할 이미지 전달
        if (loginMember == null) {
            model.addAttribute("isLogin", false);
            model.addAttribute("monthAlcohols", alcoholService.getMonthAlcohols());
            model.addAttribute("pyeonsools", alcoholService.getMonthAlcohols()); // 수정 필요

            return "mainPage";
        }

        //당신의 편술
        List<Long> mykeywords = alcoholService.getMyKeywords(loginMember.getId());
        List<Long> alcoholIds = alcoholService.getAlcohols(mykeywords);
        List<MainPageDto> pyeonsools = alcoholService.getYourAlcohols(alcoholIds);

        model.addAttribute("isLogin", true);
        model.addAttribute("monthAlcohols", alcoholService.getMonthAlcohols());
        model.addAttribute("pyeonsools", pyeonsools);

        /*마이 키워드*/
        if (loginMember!=null) {
            List<String> myKeywords = memberService.getMyKeywordsKOR(loginMember.getId());
            model.addAttribute("personalKeywords", myKeywords);
        }
        return "mainPage";
    }
}
