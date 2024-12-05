package devjay.springlab.web.interceptor;

import devjay.springlab.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class GradeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        User user = (User) request.getSession(false).getAttribute("sid");
        int grade = user.getGrade().getLevel();

        if (requestURI.startsWith("/users/" + user.getId())) {
            return true;
        }

        if (requestURI.startsWith("/items") && grade < 2) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        if (requestURI.startsWith("/users") && grade < 3) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        if (requestURI.startsWith("/actions") && grade < 3) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;  // 요청을 허용
    }
}
