package devjay.springlab;

import devjay.springlab.web.argumentresolver.LoginArgumentResolver;
import devjay.springlab.web.filter.LoginFilter;
import devjay.springlab.web.interceptor.ActionHandleInterceptor;
import devjay.springlab.web.interceptor.GradeInterceptor;
import devjay.springlab.web.interceptor.LoginCheckInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final ActionHandleInterceptor actionHandleInterceptor;
    private final GradeInterceptor gradeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css/**", "*.ico", "/views/*", "/error", "/features", "/auth/register",
                        "/auth/login", "/auth/logout");

        registry.addInterceptor(gradeInterceptor)
                .order(2)
                .addPathPatterns("/items/**", "/users/*", "/actions/**");

        registry.addInterceptor(actionHandleInterceptor)
                .order(3)
                .addPathPatterns("/items/**", "/users/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
