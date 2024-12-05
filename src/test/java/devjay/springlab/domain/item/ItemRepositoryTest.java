package devjay.springlab.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    private MemoryItemRepository memoryItemRepository;

    @AfterEach
    void afterEach() {
        memoryItemRepository.clearStore();
    }

    @Test
    void save() {
        // GIVEN
        Item item = createItem("itemA");

        // WHEN
        Item savedItem = memoryItemRepository.save(item);

        // THEN
        assertThat(savedItem.getName()).isEqualTo(item.getName());
    }

    @Test
    void validateExistUsername() {
        // GIVEN
        Item itemA = createItem("itemA");
        Item itemACopy = createItem("itemA");

        // WHEN
        memoryItemRepository.save(itemA);

        // THEN
        assertThrows(IllegalStateException.class, () -> {
            memoryItemRepository.save(itemACopy);
        });
    }


    @Test
    void findOneById() {
        // GIVEN
        Item item = createItem("itemA");
        memoryItemRepository.save(item);

        // WHEN
        Item findItem = memoryItemRepository.findOneById(1L);

        // THEN
        assertThat(findItem.getName()).isEqualTo(item.getName());
    }

    @Test
    void findOneByItemName() {
        // GIVEN
        Item item = createItem("itemA");
        memoryItemRepository.save(item);

        // WHEN
        Item findItem = memoryItemRepository.findOneByItemName(item.getName());

        // THEN
        assertThat(findItem.getName()).isEqualTo(item.getName());
    }

    @Test
    void findAll() {
        // GIVEN
        Item itemA = createItem("itemA");
        Item itemB = createItem("itemB");
        memoryItemRepository.save(itemA);
        memoryItemRepository.save(itemB);

        // WHEN
        List<Item> items = memoryItemRepository.findAll();

        // THEN
        assertThat(items.size()).isEqualTo(2);
    }

    private static Item createItem(String name) {
        return new Item(name, 1000, 10);
    }
}