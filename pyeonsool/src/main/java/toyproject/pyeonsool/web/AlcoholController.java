package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.Pagination;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.service.*;

import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/alcohols")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;
    private final ReviewService reviewService;


    @GetMapping
    public String getListPage(@RequestParam String alcoholType,
                              @PageableDefault(sort = "id",size = 8, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        List<AlcoholTypeDto> findTypeAlcohol= alcoholService.findTypeAlcohol(AlcoholType.valueOf(alcoholType),pageable);
        model.addAttribute("typeList",findTypeAlcohol);
        /*최신 등록순*/
        for (AlcoholTypeDto a : findTypeAlcohol){
            System.out.println(alcoholType + "= "+a);
        }
        return "listPage";

    }

    @GetMapping("/{alcoholId}")
    public String getDetailPage(
            @PathVariable Long alcoholId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        AlcoholDetailsDto alcoholDetails =
                alcoholService.getAlcoholDetails(alcoholId, getLoginMemberId(loginMember));
        model.addAttribute("alcoholId", alcoholId);
        model.addAttribute("alcoholDetails", alcoholDetails);
        model.addAttribute("reviewSaveForm", new ReviewSaveForm(alcoholId));
        Page<ReviewDto> reviewPage = reviewService.getReviewPage(pageable, alcoholId, getLoginMemberId(loginMember));
        model.addAttribute("reviewPagination", Pagination.of(reviewPage, 5));
        model.addAttribute("reviews", reviewPage.getContent());

        return "detailPage";
    }

    private Long getLoginMemberId(LoginMember loginMember) {
        return isNull(loginMember) ? null : loginMember.getId();
    }
}
