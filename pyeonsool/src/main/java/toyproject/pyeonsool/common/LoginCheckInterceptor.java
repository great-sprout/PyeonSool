package toyproject.pyeonsool.common;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;
import static toyproject.pyeonsool.common.exception.api.ApiExceptionType.MUST_LOGIN;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (isValidSession(session)) {
            return true;
        }

        if (isJsonRequest(request)) {
            throw MUST_LOGIN.getException();
        }

        if (!isGetRequest(request)){
            response.sendRedirect("/members/login");
            return false;
        }

        return true;
    }

    private static boolean isJsonRequest(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().equals("application/json");
    }

    private static boolean isGetRequest(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.GET.toString());
    }

    private static boolean isValidSession(HttpSession session) {
        return session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null;
    }
}
