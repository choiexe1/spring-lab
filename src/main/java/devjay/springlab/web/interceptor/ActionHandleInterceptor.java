package devjay.springlab.web.interceptor;

import devjay.springlab.domain.action.Action;
import devjay.springlab.domain.action.ActionService;
import devjay.springlab.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        User user = getUser(request);

        String methodName = handlerMethod.getMethod().getName();
        String controllerName = handlerMethod.getBeanType().getSimpleName();

        Action action = new Action(user.getUsername(), user.getId(), controllerName, methodName);
        actionService.save(action);
    }


    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("sid");
        return user;
    }
}
