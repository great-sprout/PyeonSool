package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import toyproject.pyeonsool.service.ReviewService;

import java.util.ArrayList;

import static java.util.Objects.*;

@Controller
@RequestMapping("/alcohols")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;
    private final ReviewService reviewService;

    @GetMapping
    public String getListPage() {
        return "listPage";
    }

    @GetMapping("/{alcoholId}")
    public String getDetailPage(
            @PathVariable Long alcoholId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        AlcoholDetailsDto alcoholDetails =
                alcoholService.getAlcoholDetails(alcoholId, isNull(loginMember) ? null : loginMember.getId());
        model.addAttribute("alcoholDetails", alcoholDetails);
        model.addAttribute("reviewSaveForm", new ReviewSaveForm(alcoholId));
        model.addAttribute("reviews", reviewService.getReviews(pageable, alcoholId));

        return "detailPage";
    }
}
