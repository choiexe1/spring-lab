package devjay.springlab.domain.item;

import java.util.List;

public interface ItemRepository {
    Item save(Item item);

    Item findOneById(Long id);

    Item findOneByItemName(String itemName);

    List<Item> findAll();

    void update(Long id, Item updateItem);

    void delete(Long id);
}
