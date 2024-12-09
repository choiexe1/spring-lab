package devjay.springlab.domain.user;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@ToString
public class User {
    @Setter
    private Long id;
    private final String username;
    private final String name;

    @Setter
    private Grade grade;
    private final String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt = LocalDateTime.now();

    public User(String username, String name, Grade grade, String password) {
        this.username = username;
        this.name = name;
        this.grade = grade;
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}
