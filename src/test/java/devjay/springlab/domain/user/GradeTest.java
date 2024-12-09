package devjay.springlab.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void getLevel() {
        // GIVEN
        Grade user = Grade.USER;
        Grade manager = Grade.MANAGER;
        Grade admin = Grade.ADMIN;

        // WHEN
        boolean isManagerHigherLevel = user.getLevel() < manager.getLevel();
        boolean isAdminHigherLevel = manager.getLevel() < admin.getLevel();

        // THEN
        assertThat(isManagerHigherLevel).isEqualTo(true);
        assertThat(isAdminHigherLevel).isEqualTo(true);
    }

    @Test
    void values() {
        // GIVEN
        // WHEN
        Grade[] grades = Grade.values();

        // THEN
        assertThat(grades.length).isEqualTo(3);
    }
}