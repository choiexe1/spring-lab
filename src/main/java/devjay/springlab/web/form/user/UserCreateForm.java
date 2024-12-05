package devjay.springlab.web.form.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserCreateForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
}
