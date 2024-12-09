package devjay.springlab.web.interceptor;

import devjay.springlab.domain.action.Action;
import devjay.springlab.domain.action.ActionService;
import devjay.springlab.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class ActionHandleInterceptor implements HandlerInterceptor {
    @Autowired
    private ActionService actionService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return;
        }

        User user = getUser(request);
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String controllerName = handlerMethod.getBeanType().getSimpleName();

        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(
                "org.springframework.web.servlet.View.pathVariables");

        boolean hasBindingErrors = false;
        if (modelAndView != null && modelAndView.getModel() != null) {
            for (String key : modelAndView.getModel().keySet()) {
                Object value = modelAndView.getModel().get(key);
                if (value instanceof org.springframework.validation.BindingResult bindingResult
                        && bindingResult.hasErrors()) {
                    hasBindingErrors = true;
                    break;
                }
            }
        }

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            if (pathVariables == null || pathVariables.isEmpty()) {
                return;
            }
        }

        if (hasBindingErrors) {
            return;
        }

        // 정상 처리된 경우 Action 저장
        Map<String, Object> params = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> params.put(key, String.join(",", value)));
        if (pathVariables != null) {
            params.putAll(pathVariables);
        }

        Action action = new Action(user.getUsername(), user.getId(), controllerName, requestMethod, requestURI,
                params);
        actionService.save(action);

    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("sid");
    }
}