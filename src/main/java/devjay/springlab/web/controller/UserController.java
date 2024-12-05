package devjay.springlab.web.controller;

import devjay.springlab.domain.user.User;
import devjay.springlab.domain.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findOneById(id);
        model.addAttribute("user", user);

        return "views/user/user";
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "views/user/users";
    }
}
