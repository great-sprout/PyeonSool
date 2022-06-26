package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.service.AlcoholDetailsDto;
import toyproject.pyeonsool.service.AlcoholService;

import java.util.ArrayList;

import static java.util.Objects.*;

@Controller
@RequestMapping("/alcohols")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;

    @GetMapping
    public String getListPage() {
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
