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
    //로그인 세션값 필요함 -> 블럭처리
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            Model model) {
        /**
         *         TODO List(alcohol_id)에서 각각에 맞는 이미지 경로 가져오자
         *         TODO List<AlcoholDetails>로 담자
         */
        List<Long> preferAlcohols = preferAlcohol.getAlcoholIds();

        List<AlcoholDetailsDto> alcoholDetailsList = new ArrayList<>();

        for (int i = 0; i < preferAlcohols.size(); i++) {
            AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(preferAlcohols.get(i),
                    isNull(loginMember) ? null : loginMember.getId());
            alcoholDetailsList.add(alcoholDetails);
        }
        model.addAttribute("alcoholDetailsList", alcoholDetailsList);
        return "mainPage";
    }
}
