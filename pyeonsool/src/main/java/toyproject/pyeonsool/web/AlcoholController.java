package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/alcohols")
@RequiredArgsConstructor
public class AlcoholController {

    private final AlcoholService alcoholService;
    private final ReviewService reviewService;
    private final MemberService memberService;


    @GetMapping
    public String getListPage(@RequestParam String alcoholType,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                              @PageableDefault(sort = "id", size = 8, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        Page<AlcoholDto> alcoholPage = alcoholService.findAlcoholPage(AlcoholType.valueOf(alcoholType), pageable);
        model.addAttribute("typeList", alcoholPage.getContent());
        model.addAttribute("typeListPagination", Pagination.of(alcoholPage, 5));/*최신 등록순*/
        //상품 목록

        List<MainPageDto> bestLikes = alcoholService.getBestLike(AlcoholType.valueOf(alcoholType),6);
        model.addAttribute("bestList",bestLikes);
        //베스트 상품 목록 ( 아직 4개)
        /*마이 키워드*/
        if (loginMember!=null) {
            List<String> myKeywords = memberService.getMyKeywordsKOR(loginMember.getId());
            model.addAttribute("personalKeywords", myKeywords);
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
        /*마이 키워드*/
        if (loginMember!=null) {
            List<String> myKeywords = memberService.getMyKeywordsKOR(loginMember.getId());
            model.addAttribute("personalKeywords", myKeywords);
        }
        return "detailPage";
    }

    private Long getLoginMemberId(LoginMember loginMember) {
        return isNull(loginMember) ? null : loginMember.getId();
    }
}
