package devjay.springlab.domain.user;

import devjay.springlab.domain.exception.UserExistException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public void save(User user) {
        user.setId(++sequence);
        validateExistUsername(user);

        store.put(user.getId(), user);
    }

    private void validateExistUsername(User user) {
        if (findOneByUsername(user.getUsername()) != null) {
            throw new UserExistException();
        }
    }

    @Override
    public User findOneById(Long id) {
        return store.get(id);
    }

    public User findOneByUsername(String username) {
        return store.values().stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
        sequence = 0L;
    }
}
