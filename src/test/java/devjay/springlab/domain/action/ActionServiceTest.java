package devjay.springlab.domain.action;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActionServiceTest {
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    ActionService actionService;

    @AfterEach
    void clear() {
        if (actionRepository instanceof MemoryActionRepository memoryActionRepository) {
            memoryActionRepository.clearStore();
        }
    }

    @Test
    void save() {
        // GIVEN
        Action actionA = createAction("actionA", 1L);

        // WHEN
        actionService.save(actionA);
        List<Action> actions = actionService.findAll();

        // THEN
        assertThat(actions.size()).isEqualTo(1);
    }

    @Test
    void findAll() {
        // GIVEN
        Action actionA = createAction("actionA", 1L);
        Action actionB = createAction("actionB", 2L);

        actionService.save(actionA);
        actionService.save(actionB);

        // WHEN
        List<Action> actions = actionService.findAll();

        // THEN
        assertThat(actions.size()).isEqualTo(2);

    }

    private Action createAction(String username, Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", LocalDateTime.now());
        paramMap.put("username", username);

        return new Action(
                username, id,
                "TestController", "POST",
                "/test", paramMap);
    }
}