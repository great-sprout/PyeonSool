package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.pyeonsool.service.AlcoholDetailsDto;
import toyproject.pyeonsool.service.AlcoholService;

@Controller
@RequestMapping("/alcohols")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;

    @GetMapping("/{alcoholId}")
    public String getDetailPage(@PathVariable Long alcoholId,
                                @SessionAttribute(name = "memberId", required = false) Long memberId,
                                Model model) {
        AlcoholDetailsDto alcoholDetails = alcoholService.getAlcoholDetails(alcoholId, memberId);
        model.addAttribute("alcoholDetails", alcoholDetails);

        return "detailPage";
    }
}
