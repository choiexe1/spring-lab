package devjay.springlab.domain.action;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryActionRepository implements ActionRepository {
    private static final Deque<Action> store = new ArrayDeque<>();

    @Override
    public void save(Action action) {
        if (store.size() >= 5) {
            store.pollLast();
        }

        store.offerFirst(action);
    }

    @Override
    public List<Action> findAll() {
        return new ArrayList<>(store);
    }
}
