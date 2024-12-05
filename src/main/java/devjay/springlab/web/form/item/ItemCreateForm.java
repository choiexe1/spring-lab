package devjay.springlab.web.form.item;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ItemCreateForm {
    @NotEmpty
    private String name;
    @Range(min = 1_000, max = 100_000)
    private int price;
    @Range(min = 1, max = 10000)
    private int quantity;
}
