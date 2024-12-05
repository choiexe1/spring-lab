package devjay.springlab.web.form.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
