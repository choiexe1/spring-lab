package devjay.springlab.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import devjay.springlab.domain.exception.UserExistException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private MemoryUserRepository memoryUserRepository;

    @AfterEach
    void afterEach() {
        memoryUserRepository.clearStore();
    }

    @Test
    void save() {
        // GIVEN
        User user = createUser("test");
        memoryUserRepository.save(user);

        // WHEN
        User findUser = memoryUserRepository.findOneByUsername(user.getUsername());

        // THEN
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void validateExistUsername() {
        // GIVEN
        User userA = createUser("testA");
        User userB = createUser("testA");

        // WHEN
        memoryUserRepository.save(userA);

        // THEN
        assertThrows(UserExistException.class, () -> {
            memoryUserRepository.save(userB);
        });
    }

    @Test
    void findOneById() {
        // GIVEN
        User user = createUser("test");
        memoryUserRepository.save(user);

        // WHEN
        User findUser = memoryUserRepository.findOneById(user.getId());

        // THEN
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(findUser.getId()).isEqualTo(1L);
    }

    @Test
    void findOneByUsername() {
        // GIVEN
        User user = createUser("test");
        memoryUserRepository.save(user);

        // WHEN
        User findUser = memoryUserRepository.findOneByUsername(user.getUsername());

        // THEN
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void findAll() {
        // GIVEN
        User userA = createUser("testA");
        User userB = createUser("testB");

        memoryUserRepository.save(userA);
        memoryUserRepository.save(userB);

        // WHEN
        List<User> users = memoryUserRepository.findAll();

        // THEN
        assertThat(users.size()).isEqualTo(2);
    }

    private User createUser(String username) {
        return new User(username, "tester", Grade.USER, "test!");
    }
}