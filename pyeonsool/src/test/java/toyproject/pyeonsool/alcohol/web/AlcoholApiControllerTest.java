package toyproject.pyeonsool.alcohol.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlcoholApiController.class)
class AlcoholApiControllerTest {

    MockMvc mvc;

    @MockBean
    AlcoholService alcoholService;

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
    class LikeAlcoholTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/alcohols/2/like")
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
            mvc.perform(post("/alcohols/2/like")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_AlcoholIdIsInvalid() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/alcohols/-1/like")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("유효하지 않은 술 고유 번호입니다."));
        }
    }

    @Nested
    class DislikeAlcoholTest {
        @Test
        void should_Success() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/alcohols/2/dislike")
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
            mvc.perform(post("/alcohols/2/dislike")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.status").value("401"))
                    .andExpect(jsonPath("$.message").value("로그인 후 이용해주세요."));
        }

        @Test
        void should_Fail_When_AlcoholIdIsInvalid() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(1L, "nickname"));

            //when
            //then
            mvc.perform(post("/alcohols/-1/dislike")
                            .session(session)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("400"))
                    .andExpect(jsonPath("$.message").value("유효하지 않은 술 고유 번호입니다."));
        }
    }

}