package toyproject.pyeonsool.alcohol.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.alcohol.sevice.AlcoholDetailsDto;
import toyproject.pyeonsool.alcohol.sevice.AlcoholImageDto;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.Pagination;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.member.sevice.MemberService;
import toyproject.pyeonsool.member.web.MemberController;
import toyproject.pyeonsool.review.sevice.ReviewDto;
import toyproject.pyeonsool.review.sevice.ReviewService;
import toyproject.pyeonsool.review.web.ReviewSaveRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.data.domain.Sort.Order.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlcoholController.class)
class AlcoholControllerTest {

    MockMvc mvc;

    @MockBean
    MemberService memberService;

    @MockBean
    AlcoholService alcoholService;

    @MockBean
    ReviewService reviewService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    class GetDetailPageTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");
            AlcoholDetailsDto alcoholDetails = AlcoholDetailsDto.of(
                    alcohol, alcohol.getFileName(), "3", List.of(), List.of(), true);
            PageImpl<ReviewDto> reviewPage = new PageImpl<>(
                    List.of(), PageRequest.of(0, 10, Sort.by(desc("id"))), 0);
            List<AlcoholImageDto> relatedAlcohols = List.of();
            List<String> personalKeywords = List.of();

            given(alcoholService.getAlcoholDetails(anyLong(), anyLong()))
                    .willReturn(alcoholDetails);
            given(reviewService.getReviewPage(any(), anyLong(), anyLong()))
                    .willReturn(reviewPage);
            given(alcoholService.getRelatedAlcohols(anyLong()))
                    .willReturn(relatedAlcohols);
            given(memberService.getMyKeywordsKOR(anyLong()))
                    .willReturn(personalKeywords);

            //when
            //then
            long alcoholId = 2L;
            mvc.perform(get("/alcohols/2")
                            .session(session))
                    .andExpect(view().name("detailPage"))
                    .andExpect(model().attribute("alcoholId", alcoholId))
                    .andExpect(model().attribute("alcoholDetails", alcoholDetails))
                    .andExpect(model().attribute("reviewSaveForm",  new ReviewSaveRequest(alcoholId)))
                    .andExpect(model().attribute("reviewPagination",  Pagination.of(reviewPage, 5)))
                    .andExpect(model().attribute("reviews",  reviewPage.getContent()))
                    .andExpect(model().attribute("relatedAlcohols",  relatedAlcohols))
                    .andExpect(model().attribute("relatedAlcohols",  relatedAlcohols))
                    .andExpect(model().attribute("personalKeywords",  personalKeywords));
        }

        @Test
        void should_Fail_When_AlcoholIdIsNotPositive() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            Alcohol alcohol = new Alcohol(AlcoholType.WINE, "test.jpg", "옐로우테일", 35000, 13.5f,
                    (byte) 3, (byte) 2, "우리집", "대한민국");
            AlcoholDetailsDto alcoholDetails = AlcoholDetailsDto.of(
                    alcohol, alcohol.getFileName(), "3", List.of(), List.of(), true);
            PageImpl<ReviewDto> reviewPage = new PageImpl<>(
                    List.of(), PageRequest.of(0, 10, Sort.by(desc("id"))), 0);
            List<AlcoholImageDto> relatedAlcohols = List.of();
            List<String> personalKeywords = List.of();

            given(alcoholService.getAlcoholDetails(anyLong(), anyLong()))
                    .willReturn(alcoholDetails);
            given(reviewService.getReviewPage(any(), anyLong(), anyLong()))
                    .willReturn(reviewPage);
            given(alcoholService.getRelatedAlcohols(anyLong()))
                    .willReturn(relatedAlcohols);
            given(memberService.getMyKeywordsKOR(anyLong()))
                    .willReturn(personalKeywords);

            //when
            //then
            mvc.perform(get("/alcohols/0")
                            .session(session))
                    .andExpect(status().isBadRequest());
        }
    }
}