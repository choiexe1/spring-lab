package devjay.springlab.domain.action;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;

    public void save(Action action) {
        actionRepository.save(action);
    }

    public List<Action> findAll() {
        return actionRepository.findAll();
    }
}
