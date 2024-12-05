package devjay.springlab.web.controller;

import devjay.springlab.domain.item.Item;
import devjay.springlab.domain.item.ItemService;
import devjay.springlab.web.form.item.ItemCreateForm;
import devjay.springlab.web.form.item.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public String itemsView(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "views/item/items";
    }


    @GetMapping("/add")
    public String itemAddView(@ModelAttribute("item") ItemCreateForm item) {
        return "views/item/add";
    }

    @PostMapping("/add")
    public String itemAdd(@Validated @ModelAttribute("item") ItemCreateForm form,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/item/add";
        }

        Item newItem = new Item(form.getName(), form.getPrice(), form.getQuantity());
        try {
            itemService.save(newItem);

        } catch (IllegalStateException e) {
            bindingResult.rejectValue("name", "items.name.exist");
            return "views/item/add";
        }

        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String itemEditView(@PathVariable("id") Long id, Model model) {
        Item item = itemService.findOneById(id);
        ItemUpdateForm itemUpdateForm = new ItemUpdateForm(item.getName(), item.getPrice(), item.getQuantity());

        model.addAttribute("form", itemUpdateForm);

        return "views/item/edit";
    }

    @PostMapping("/edit/{id}")
    public String itemEdit(@PathVariable("id") Long id, @Validated @ModelAttribute("form") ItemUpdateForm form,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/item/edit";
        }

        Item updateItem = new Item(form.getName(), form.getPrice(), form.getQuantity());
        try {
            itemService.update(id, updateItem);
        } catch (IllegalStateException e) {
            bindingResult.rejectValue("name", "items.name.exist");
            return "views/item/edit";
        }

        return "redirect:/items";
    }

    @PostMapping("/delete/{id}")
    public String itemDelete(@PathVariable("id") Long id) {
        itemService.delete(id);

        return "redirect:/items";
    }
}
