package devjay.springlab.domain.action;

import java.util.List;

public interface ActionRepository {
    void save(Action action);

    List<Action> findAll();
}
