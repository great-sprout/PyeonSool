package toyproject.pyeonsool.alcohol.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.Pagination;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.alcohol.sevice.AlcoholDto;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.VendorName;
import toyproject.pyeonsool.alcohol.repository.AlcoholSearchConditionDto;
import toyproject.pyeonsool.member.sevice.MemberService;
import toyproject.pyeonsool.review.sevice.ReviewDto;
import toyproject.pyeonsool.review.sevice.ReviewService;
import toyproject.pyeonsool.review.web.ReviewSaveRequest;

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
    public String getListPage(@ModelAttribute AlcoholSearchForm alcoholSearchForm,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
                              @PageableDefault(sort = {"likeCount"}, size = 8, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        Page<AlcoholDto> alcoholPage = alcoholService.findAlcoholPage(
                pageable,
                new AlcoholSearchConditionDto(
                        getAlcoholType(alcoholSearchForm),
                        alcoholSearchForm.getKeywords(),
                        alcoholSearchForm.getSearch(),
                        getVendorName(alcoholSearchForm)
                ));

        model.addAttribute("typeList", alcoholPage.getContent());
        model.addAttribute("typeListPagination", Pagination.of(alcoholPage, 5));/*최신 등록순*/
        //상품 목록
        model.addAttribute(
                "bestList", alcoholService.getBestLike(getAlcoholType(alcoholSearchForm), 6));
        //베스트 상품 목록 ( 아직 4개)

        /*마이 키워드*/
        if (loginMember!=null) {
            List<String> myKeywords = memberService.getMyKeywordsKOR(loginMember.getId());
            model.addAttribute("personalKeywords", myKeywords);
        }

        return "listPage";
    }

    private AlcoholType getAlcoholType(AlcoholSearchForm alcoholSearchForm) {
        if (!StringUtils.hasText(alcoholSearchForm.getAlcoholType())) {
            return null;
        }

        return AlcoholType.valueOf(alcoholSearchForm.getAlcoholType());
    }

    private VendorName getVendorName(AlcoholSearchForm alcoholSearchForm) {
        if (!StringUtils.hasText(alcoholSearchForm.getVendorName())) {
            return null;
        }

        return VendorName.valueOf(alcoholSearchForm.getVendorName());
    }

    @GetMapping("/{alcoholId}")
    public String getDetailPage(
            @PathVariable Long alcoholId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        model.addAttribute("alcoholId", alcoholId);
        model.addAttribute("alcoholDetails",
                alcoholService.getAlcoholDetails(alcoholId, getLoginMemberId(loginMember)));
        model.addAttribute("reviewSaveForm", new ReviewSaveRequest(alcoholId));
        Page<ReviewDto> reviewPage = reviewService.getReviewPage(pageable, alcoholId, getLoginMemberId(loginMember));
        model.addAttribute("reviewPagination", Pagination.of(reviewPage, 5));
        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("relatedAlcohols", alcoholService.getRelatedAlcohols(alcoholId));

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
