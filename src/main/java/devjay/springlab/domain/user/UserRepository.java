package devjay.springlab.domain.user;

import java.util.List;

public interface UserRepository {
    void save(User user);

    User findOneById(Long id);

    User findOneByUsername(String username);

    List<User> findAll();
}
