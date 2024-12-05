package devjay.springlab.domain.action;

import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class Action {
    private final String methodName;
    private final String controllerName;
    private final String username;
    private final Long userId;
    private final LocalDateTime time = LocalDateTime.now();

    public Action(String username, Long userId, String controllerName, String methodName) {
        this.username = username;
        this.userId = userId;
        this.controllerName = controllerName;
        this.methodName = methodName;
    }
}
