package toyproject.pyeonsool;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyproject.pyeonsool.common.LoginCheckInterceptor;

import javax.persistence.EntityManager;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/login", "/members/add", "/members/logout",
                        "/css/**", "/*.ico", "/js/**", "/image/**", "/alcohols",
                        "/error");
    }
}
