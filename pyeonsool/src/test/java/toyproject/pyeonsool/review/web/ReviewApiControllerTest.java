package toyproject.pyeonsool.review.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import toyproject.pyeonsool.alcohol.web.AlcoholApiController;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.review.sevice.ReviewService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewApiController.class)
class ReviewApiControllerTest {

    MockMvc mvc;

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
    class AddReviewTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"alcoholId\" : 1, \"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isOk());
        }

        @Test
        void should_Fail_When_DoNotLogin() throws Exception {
            //given
            //when
            //then
            mvc.perform(post("/reviews/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"alcoholId\" : 1, \"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_AlcoholIdIsNull() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("술 고유 번호는 필수입니다."));
        }

        @Test
        void should_Fail_When_AlcoholIdIsNotPositive() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"alcoholId\" : -1, \"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("술 고유 번호는 0보다 커야합니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 6})
        void should_Fail_When_GradeIsInvalid(int grade) throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(String.format(
                                    "{\"alcoholId\" : 1, \"grade\" :  %d, \"content\" :  \"테스트 댓글\"}", grade)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("평점은 1 ~ 5 사이로 선택하세요."));
        }

        @Test
        void should_Fail_When_GradeIsNull() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"alcoholId\" : 1, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("평점은 필수입니다."));
        }

        @Test
        void should_Fail_When_ContentIsNull() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"alcoholId\" : 1, \"grade\" :  3}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰는 공백일 수 없습니다."));
        }

        @Test
        void should_Fail_When_ContentLengthIsGreaterThan300() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < 301; i++) {
                content.append("ㅁ");
            }

            //when
            //then
            mvc.perform(post("/reviews/add")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(String.format(
                                    "{\"alcoholId\" : 1, \"grade\" :  3, \"content\" :  \"%s\"}", content)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰는 300자 이내로 작성하세요."));
        }
    }

    @Nested
    class EditReviewTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(patch("/reviews/2/edit")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isOk());
        }

        @Test
        void should_Fail_When_DoNotLogin() throws Exception {
            //given
            //when
            //then
            mvc.perform(patch("/reviews/2/edit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_ReviewIdIsNotPositive() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(patch("/reviews/0/edit")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"grade\" :  3, \"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰 고유 번호는 0보다 커야합니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 6})
        void should_Fail_When_GradeIsInvalid(int grade) throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(patch("/reviews/2/edit")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(String.format(
                                    "{\"grade\" :  %d, \"content\" :  \"테스트 댓글\"}", grade)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("평점은 1 ~ 5 사이로 선택하세요."));
        }

        @Test
        void should_Fail_When_GradeIsNull() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(patch("/reviews/2/edit")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"content\" :  \"테스트 댓글\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("평점은 필수입니다."));
        }

        @Test
        void should_Fail_When_ContentIsNull() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(patch("/reviews/2/edit")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content("{\"grade\" :  3}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰는 공백일 수 없습니다."));
        }

        @Test
        void should_Fail_When_ContentLengthIsGreaterThan300() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < 301; i++) {
                content.append("ㅁ");
            }

            //when
            //then
            mvc.perform(patch("/reviews/2/edit")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(String.format(
                                    "{\"grade\" :  3, \"content\" :  \"%s\"}", content)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰는 300자 이내로 작성하세요."));
        }
    }

    @Nested
    class RecommendReviewTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/2/recommend")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());
        }

        @Test
        void should_Fail_When_DoNotLogin() throws Exception {
            //given
            //when
            //then
            mvc.perform(post("/reviews/2/recommend")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_ReviewIdIsNotPositive() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/0/recommend")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰 고유 번호는 0보다 커야합니다."));
        }
    }

    @Nested
    class NotRecommendReviewTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/2/not-recommend")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());
        }

        @Test
        void should_Fail_When_DoNotLogin() throws Exception {
            //given
            //when
            //then
            mvc.perform(post("/reviews/2/not-recommend")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_ReviewIdIsNotPositive() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/reviews/0/not-recommend")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰 고유 번호는 0보다 커야합니다."));
        }
    }

    @Test
    void cancelRecommendation() {
    }

    @Test
    void cancelNotRecommendation() {
    }

    @Nested
    class DeleteReviewTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(delete("/reviews/2/delete")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());
        }

        @Test
        void should_Fail_When_DoNotLogin() throws Exception {
            //given
            //when
            //then
            mvc.perform(delete("/reviews/2/delete")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_ReviewIdIsNotPositive() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(delete("/reviews/0/delete")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("리뷰 고유 번호는 0보다 커야합니다."));
        }
    }
}