package devjay.springlab.domain.item;

import devjay.springlab.domain.exception.ItemNameExistException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryItemRepository implements ItemRepository {
    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private long sequence = 0L;

    @Override
    public Item save(Item item) {
        validateExistItemName(item);

        item.setId(++sequence);
        store.put(sequence, item);

        return item;
    }

    private void validateExistItemName(Item item) {
        if (findOneByItemName(item.getName()) != null) {
            throw new ItemNameExistException();
        }
    }

    @Override
    public Item findOneById(Long id) {
        return store.get(id);
    }

    @Override
    public Item findOneByItemName(String itemName) {
        return store.values().stream()
                .filter(item -> item.getName().equals(itemName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void update(Long id, Item updateItem) {
        Item target = findOneById(id);

        if (!target.getName().equals(updateItem.getName())) {
            validateExistItemName(updateItem);
        }

        target.setName(updateItem.getName());
        target.setPrice(updateItem.getPrice());
        target.setQuantity(updateItem.getQuantity());
    }

    public void clearStore() {
        store.clear();
        sequence = 0L;
    }
}