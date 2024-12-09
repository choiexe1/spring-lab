package devjay.springlab.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    @AfterEach
    void clear() {
        /**
         * 유저 서비스는 현재 웹 브라우저에서 편의를 위해 @PostConstruct를 통한 초기화 메서드로 스프링 컨테이너 생성 시점에 유저를 추가한다.
         * 따라서 @BeforeEach와 @AfterEach 모두 적용하여 리포지토리를 클리어 한다.
         */
        if (userRepository instanceof MemoryUserRepository memoryUserRepository) {
            memoryUserRepository.clearStore();
        }
    }

    @Test
    void register() {
        // GIVEN
        User user = new User("tester", "tester", Grade.USER, "tester");

        // WHEN
        userService.register(user);
        User findUser = userService.findOneById(1L);

        // THEN
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void findOneById() {
        // GIVEN
        User user = new User("tester", "tester", Grade.USER, "tester");
        userService.register(user);

        // WHEN
        User findUser = userService.findOneById(1L);

        // THEN
        assertThat(findUser.getId()).isEqualTo(1L);
    }

    @Test
    void findAll() {
        // GIVEN
        User userA = new User("testerA", "testerA", Grade.USER, "testerA");
        User userB = new User("testerB", "testerB", Grade.USER, "testerB");
        userService.register(userA);
        userService.register(userB);

        // WHEN
        List<User> users = userService.findAll();

        // THEN
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void findOneByUsername() {
        // GIVEN
        User user = new User("tester", "tester", Grade.USER, "tester");

        // WHEN
        userService.register(user);
        User findUser = userService.findOneByUsername("tester");

        // THEN
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
    }
}