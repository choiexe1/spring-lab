package devjay.springlab.web.controller;

import devjay.springlab.domain.action.Action;
import devjay.springlab.domain.action.ActionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/actions")
public class ActionController {
    private final ActionService actionService;

    @GetMapping
    public String actionsView(Model model) {
        List<Action> actions = actionService.findAll();
        model.addAttribute("actions", actions);

        return "views/action/actions";
    }
}
