package devjay.springlab.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private long id;
    private String name;
    private int price;
    private int quantity;

    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return quantity * price;
    }

    public void removeQuantity(int quantity) {
        int totalQuantity = this.quantity - quantity;

        if (totalQuantity < 0) {
            throw new ArithmeticException("수량이 0보다 작아질 수 없습니다.");
        }

        this.quantity = totalQuantity;
    }
}
