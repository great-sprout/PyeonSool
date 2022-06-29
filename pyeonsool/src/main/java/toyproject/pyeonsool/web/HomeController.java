package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.repository.PreferredAlcoholCustomRepositoryImpl;
import toyproject.pyeonsool.service.AlcoholDetailsDto;
import toyproject.pyeonsool.service.AlcoholService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AlcoholService alcoholService;
    private final PreferredAlcoholCustomRepositoryImpl preferAlcohol;


    @GetMapping("/")
    //로그인 세션값 필요함 -> 당신의 편술 블럭처리
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            Model model)
    {
        //이달의 추천
        List<Long> preferAlcohols = preferAlcohol.getAlcoholIds();
        List<AlcoholDetailsDto> alcoholDetailsList = new ArrayList<>();
        for (int i = 0; i < preferAlcohols.size(); i++) {
            AlcoholDetailsDto alcoholDetails =
                    alcoholService.getAlcoholDetails(preferAlcohols.get(i),
                    isNull(loginMember) ? null : loginMember.getId());
            alcoholDetailsList.add(alcoholDetails);
        }

        //베스트 Like
        List<Long> preferSojus = preferAlcohol.getSojus();
        List<AlcoholDetailsDto> sojuDetailsList = new ArrayList<>();
        for (int i = 0; i < preferSojus.size(); i++) {
            AlcoholDetailsDto alcoholDetails =
                    alcoholService.getAlcoholDetails(preferSojus.get(i),
                    isNull(loginMember) ? null : loginMember.getId());
            sojuDetailsList.add(alcoholDetails);
        }
        List<Long> preferBeers = preferAlcohol.getBeers();
        List<AlcoholDetailsDto> beerDetailsList = new ArrayList<>();
        for (int i = 0; i < preferBeers.size(); i++) {
            AlcoholDetailsDto alcoholDetails =
                    alcoholService.getAlcoholDetails(preferBeers.get(i),
                            isNull(loginMember) ? null : loginMember.getId());
            beerDetailsList.add(alcoholDetails);
        }
        List<Long> preferWines = preferAlcohol.getWines();
        List<AlcoholDetailsDto> wineDetailsList = new ArrayList<>();
        for (int i = 0; i < preferWines.size(); i++) {
            AlcoholDetailsDto alcoholDetails =
                    alcoholService.getAlcoholDetails(preferWines.get(i),
                            isNull(loginMember) ? null : loginMember.getId());
            wineDetailsList.add(alcoholDetails);
        }

        model.addAttribute("alcoholDetailsList", alcoholDetailsList);
        model.addAttribute("sojuDetailsList", sojuDetailsList);
        model.addAttribute("beerDetailsList", beerDetailsList);
        model.addAttribute("wineDetailsList", wineDetailsList);
        return "mainPage";
    }
}
