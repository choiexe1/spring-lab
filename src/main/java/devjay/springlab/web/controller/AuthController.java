package devjay.springlab.web.controller;

import devjay.springlab.domain.exception.UserExistException;
import devjay.springlab.domain.user.Grade;
import devjay.springlab.domain.user.User;
import devjay.springlab.domain.user.UserService;
import devjay.springlab.web.form.user.UserCreateForm;
import devjay.springlab.web.form.user.UserLoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerView(@ModelAttribute("createForm") UserCreateForm form) {
        return "views/auth/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("createForm") UserCreateForm form, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "views/auth/register";
        }

        User user = new User(form.getUsername(), form.getName(), Grade.USER, form.getPassword());

        try {
            userService.register(user);
        } catch (UserExistException e) {
            bindingResult.reject(e.getErrorCode());
            return "views/auth/register";
        }

        redirectAttributes.addAttribute("status", true);

        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginView(@ModelAttribute("loginForm") UserLoginForm form) {
        return "views/auth/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") UserLoginForm form, BindingResult bindingResult,
                        HttpServletRequest req, @RequestParam(defaultValue = "/") String redirectURL) {

        if (bindingResult.hasErrors()) {
            log.debug("bindingResult = {}", bindingResult);
            return "views/auth/login";
        }

        User user = userService.findOneByUsername(form.getUsername());

        if (user != null) {
            if (user.verifyPassword(form.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("sid", user);

                return "redirect:" + redirectURL;
            } else {
                bindingResult.rejectValue("password", "user.password.notMatch");
            }
        } else {
            bindingResult.rejectValue("username", "user.username.notExist");
        }

        return "views/auth/login";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            try {
                session.invalidate();
            } catch (IllegalStateException e) {
                return "redirect:/";
            }
        }

        return "redirect:/";
    }
}
