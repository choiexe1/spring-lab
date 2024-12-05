package devjay.springlab.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j

public class LoginFilter implements Filter {
    private static final String[] whitelist = {"/", "/login", "/logout", "/css/*", "/features", "/auth/*", "/error/*"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String requestURI = req.getRequestURI();

        try {
            if (isLoginCheckPath(requestURI)) {
                HttpSession session = req.getSession(false);

                if (session == null || session.getAttribute("sid") == null) {

                    // 로그인으로 Redirect
                    res.sendRedirect("/auth/login");
                    return;
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
