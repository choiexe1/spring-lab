package devjay.springlab.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    void afterEach() {
        if (itemRepository instanceof MemoryItemRepository memoryItemRepository) {
            memoryItemRepository.clearStore();
        }
    }

    @Test
    void saveItem() {
        // given
        Item item = new Item("Effective Java", 20000, 10);

        // WHEN
        long savedId = itemService.save(item);

        // THEN
        Item savedItem = itemRepository.findOneById(savedId);
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Effective Java");
    }

    @Test
    void findOneById() {
        // given
        Item item = new Item("Effective Java", 20000, 10);
        itemService.save(item);

        // WHEN
        Item foundItem = itemService.findOneById(item.getId());

        // THEN
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.getName()).isEqualTo("Effective Java");
    }

    @Test
    void deleteItem() {
        // GIVEN
        Item item = new Item("Effective Java", 20000, 10);
        long id = itemService.save(item);

        // WHEN
        itemService.delete(id);

        // THEN
        assertThat(itemRepository.findOneById(id)).isNull();
    }

    @Test
    void updateItem() {
        // GIVEN
        Item oldItem = new Item("Old Item", 10000, 5);
        long id = itemService.save(oldItem);

        Item updatedItem = new Item("Updated Item", 15000, 8);

        // WHEN
        itemService.update(id, updatedItem);

        // THEN
        Item foundItem = itemRepository.findOneById(id);
        assertThat(foundItem.getName()).isEqualTo("Updated Item");
        assertThat(foundItem.getPrice()).isEqualTo(15000);
        assertThat(foundItem.getQuantity()).isEqualTo(8);
    }

    @Test
    void findAllItems() {
        // GIVEN
        itemService.save(new Item("Item1", 10000, 1));
        itemService.save(new Item("Item2", 20000, 2));

        // WHEN
        List<Item> items = itemService.findAll();

        // THEN
        assertThat(items).hasSize(2);
    }
}
