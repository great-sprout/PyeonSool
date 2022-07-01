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
import toyproject.pyeonsool.service.AlcoholDetailsDto;
import toyproject.pyeonsool.service.AlcoholService;
import toyproject.pyeonsool.service.MainPageDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AlcoholService alcoholService;

    @GetMapping("/")
    //로그인 세션값 필요함 -> 당신의 편술 블럭처리
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            Model model) {

        //이달의 추천
        List<MainPageDto> monthAlcohols = alcoholService.getMonthAlcohols();
        //베스트 Like
        List<MainPageDto> sojus = alcoholService.getBestLike(AlcoholType.SOJU, 4);
        List<MainPageDto> beers = alcoholService.getBestLike(AlcoholType.BEER, 4);
        List<MainPageDto> wines = alcoholService.getBestLike(AlcoholType.WINE, 4);

        //블럭처리할 이미지 전달
        if (loginMember == null) {
            List<MainPageDto> pyeonsools = alcoholService.getMonthAlcohols();
            model.addAttribute("isLogin", false);
            model.addAttribute("monthAlcohols", monthAlcohols);
            model.addAttribute("pyeonsools", pyeonsools);
            model.addAttribute("sojus", sojus);
            model.addAttribute("beers", beers);
            model.addAttribute("wines", wines);
            return "mainPage";
        }

        //당신의 편술
        List<Long> mykeywords = alcoholService.getMykeywords(loginMember.getId());
        List<Long> alcohols = alcoholService.getAlcohols(mykeywords);
        List<MainPageDto> pyeonsools = alcoholService.getYourAlcohols(alcohols);

        model.addAttribute("isLogin", true);
//        model.addAttribute("loginmember", loginMember);
//        model.addAttribute("selectkeywords", mykeywords);
        model.addAttribute("monthAlcohols", monthAlcohols);
        model.addAttribute("pyeonsools", pyeonsools);
        model.addAttribute("sojus", sojus);
        model.addAttribute("beers", beers);
        model.addAttribute("wines", wines);

        return "mainPage";
    }
}
