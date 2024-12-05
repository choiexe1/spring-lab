package devjay.springlab.domain.item;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public long save(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    public Item findOneById(Long id) {
        return itemRepository.findOneById(id);
    }

    public Item findOneByItemName(String itemName) {
        return itemRepository.findOneByItemName(itemName);
    }

    public void update(Long id, Item updateItem) {
        itemRepository.update(id, updateItem);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void delete(Long id) {
        itemRepository.delete(id);
    }

    @PostConstruct
    private void createItems() {
        Item book1 = new Item("Effective Java (3rd Edition)", 20_000, 10);
        Item book2 = new Item("Java 8 in Action: Lambdas, Streams, and Functional-style Programming", 35_000, 5);

        itemRepository.save(book1);
        itemRepository.save(book2);
    }
}
