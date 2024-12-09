package devjay.springlab.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void verifyPassword() {
        // GIVEN
        User user = new User("testerA", "testerA", Grade.USER, "testerA");
        String password = "testerA";

        // WHEN
        boolean result = user.verifyPassword(password);

        // THEN
        assertThat(result).isEqualTo(true);
    }
}