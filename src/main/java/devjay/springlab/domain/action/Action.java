package devjay.springlab.domain.action;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@ToString
public class Action {
    private final String username;
    private final Long userId;
    private final String controller;
    private final String requestMethod;
    private final String requestURI;
    private final Map<String, Object> params;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    public Action(String username, Long userId, String controller, String requestMethod, String requestURI,
                  Map<String, Object> params) {
        this.username = username;
        this.userId = userId;
        this.controller = controller;
        this.requestMethod = requestMethod.toUpperCase();
        this.requestURI = requestURI;
        this.params = params;
    }
}
