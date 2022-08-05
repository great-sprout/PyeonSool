package toyproject.pyeonsool.member.web;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.common.exception.form.PyeonSoolFieldException;
import toyproject.pyeonsool.common.exception.form.PyeonSoolFormException;
import toyproject.pyeonsool.member.sevice.MemberService;
import toyproject.pyeonsool.review.sevice.ReviewService;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

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
    class LoginTest {
        @Test
        void should_Success_When_RedirectUrlDoesNotExist() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", "userId");
            params.add("password", "password");

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        void should_Success_When_RedirectUrlExists() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", "userId");
            params.add("password", "password");
            params.add("redirectURL", "/where");

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(redirectedUrl("/where"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        void should_Success_When_UserIdIsBlank(String userId) throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", userId);
            params.add("password", "password");

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(view().name("signIn"))
                    .andExpect(model().attributeHasFieldErrorCode("loginForm", "userId", "NotBlank"));
        }

        @Test
        void should_Success_When_UserIdDoesNotExist() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("password", "password");

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(view().name("signIn"))
                    .andExpect(model().attributeHasFieldErrorCode("loginForm", "userId", "NotBlank"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        void should_Success_When_PasswordIsBlank(String password) throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", "userId");
            params.add("password", password);

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(view().name("signIn"))
                    .andExpect(model().attributeHasFieldErrorCode("loginForm", "password", "NotBlank"));
        }

        @Test
        void should_Success_When_PasswordDoesNotExist() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", "userId");

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(view().name("signIn"))
                    .andExpect(model().attributeHasFieldErrorCode("loginForm", "password", "NotBlank"));
        }

        @Test
        void should_Success_When_MemberServiceHasFieldError() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", "userId");
            params.add("password", "password");

            given(memberService.findLoginMember(anyString(), anyString()))
                    .willThrow(new PyeonSoolFieldException("필드 에러", "error", "userId"));

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(view().name("signIn"))
                    .andExpect(model().attributeHasFieldErrorCode("loginForm", "userId", "error"));
        }

        @Test
        void should_Success_When_MemberServiceHasGlobalError() throws Exception {
            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("userId", "userId");
            params.add("password", "password");

            given(memberService.findLoginMember(anyString(), anyString()))
                    .willThrow(new PyeonSoolFormException("글로벌 에러", "error"));

            //when
            //then
            mvc.perform(post("/members/login")
                            .characterEncoding("UTF-8")
                            .params(params))
                    .andExpect(view().name("signIn"))
                    .andExpect(model().attributeHasErrors("loginForm"));
        }
    }


}