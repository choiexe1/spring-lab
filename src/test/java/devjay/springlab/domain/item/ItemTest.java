package devjay.springlab.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void getTotalPrice() {
        // GIVEN
        Item item = new Item("itemA", 1000, 10);

        // WHEN
        int totalPrice = item.getTotalPrice();

        // THEN
        Assertions.assertThat(totalPrice).isEqualTo(10_000);
    }
}