package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.service.AlcoholDetailsDto;
import toyproject.pyeonsool.service.AlcoholService;
import toyproject.pyeonsool.service.AlcoholTypeDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;
import static toyproject.pyeonsool.domain.AlcoholType.BEER;

@Controller
@RequestMapping("/alcohols")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;


    @GetMapping
    public String getListPage(@RequestParam String alcoholType,
                              Model model) {
        List<AlcoholTypeDto> findTypeAlcohol= alcoholService.findTypeAlcohol(AlcoholType.valueOf(alcoholType));
        model.addAttribute("typeList",findTypeAlcohol);



        //then
        for (AlcoholTypeDto a : findTypeAlcohol){
            System.out.println(alcoholType + "= "+a.getName());
        }
        return "listPage";

    }

    @GetMapping("/{alcoholId}")
    public String getDetailPage(
            @PathVariable Long alcoholId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            Model model) {

        AlcoholDetailsDto alcoholDetails =
                alcoholService.getAlcoholDetails(alcoholId, isNull(loginMember) ? null : loginMember.getId());
        model.addAttribute("alcoholDetails", alcoholDetails);
        model.addAttribute("reviewSaveForm", new ReviewSaveForm(alcoholId));
        model.addAttribute("stars", createStars());

        return "detailPage";
    }

    private ArrayList<Integer> createStars() {
        ArrayList<Integer> stars = new ArrayList<>();
        for (int star = 5; star >= 1; star--) {
            stars.add(star);
        }

        return stars;
    }
}
