package devjay.springlab.domain.user;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void register(User user) {
        userRepository.save(user);
    }

    public User findOneById(Long id) {
        return userRepository.findOneById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @PostConstruct
    public void createTester() {
        userRepository.save(new User("test", "테스터", Grade.ADMIN, "test"));
        userRepository.save(new User("user", "김유저", Grade.USER, "user"));
        userRepository.save(new User("manager", "최매니저", Grade.MANAGER, "manager"));
    }
}
