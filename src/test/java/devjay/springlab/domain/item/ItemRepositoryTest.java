package devjay.springlab.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import devjay.springlab.domain.exception.ItemNameExistException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    void afterEach() {
        if (itemRepository instanceof MemoryItemRepository memoryItemRepository) {
            memoryItemRepository.clearStore();
        }
    }

    @Test
    void save() {
        // GIVEN
        Item item = createItem("itemA");

        // WHEN
        Item savedItem = itemRepository.save(item);

        // THEN
        assertThat(savedItem.getName()).isEqualTo(item.getName());
    }

    @Test
    void validateExistUsername() {
        // GIVEN
        Item itemA = createItem("itemA");
        Item itemACopy = createItem("itemA");

        // WHEN
        itemRepository.save(itemA);

        // THEN
        assertThrows(ItemNameExistException.class, () -> {
            itemRepository.save(itemACopy);
        });
    }


    @Test
    void findOneById() {
        // GIVEN
        Item item = createItem("itemA");
        itemRepository.save(item);

        // WHEN
        Item findItem = itemRepository.findOneById(1L);

        // THEN
        assertThat(findItem.getName()).isEqualTo(item.getName());
    }

    @Test
    void findOneByItemName() {
        // GIVEN
        Item item = createItem("itemA");
        itemRepository.save(item);

        // WHEN
        Item findItem = itemRepository.findOneByItemName(item.getName());

        // THEN
        assertThat(findItem.getName()).isEqualTo(item.getName());
    }

    @Test
    void findAll() {
        // GIVEN
        Item itemA = createItem("itemA");
        Item itemB = createItem("itemB");
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // WHEN
        List<Item> items = itemRepository.findAll();

        // THEN
        assertThat(items.size()).isEqualTo(2);
    }

    private static Item createItem(String name) {
        return new Item(name, 1000, 10);
    }
}