package devjay.springlab.domain.action;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ActionRepositoryTest {

    @Autowired
    private ActionRepository actionRepository;

    @AfterEach
    void afterEach() {
        if (actionRepository instanceof MemoryActionRepository memoryActionRepository) {
            memoryActionRepository.clearStore();
        }
    }

    @Test
    void save() {
        // GIVEN
        Action action = createAction("jay1", 100L);

        // WHEN
        actionRepository.save(action);
        Action findAction = actionRepository.findAll().removeFirst();

        // THEN
        assertThat(findAction.getUserId()).isEqualTo(action.getUserId());
    }

    @Test
    void findAll() {
        // GIVEN
        Action action = createAction("jay1", 100L);

        // WHEN
        actionRepository.save(action);
        Action findAction = actionRepository.findAll().removeFirst();

        // THEN
        assertThat(findAction.getUserId()).isEqualTo(action.getUserId());
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