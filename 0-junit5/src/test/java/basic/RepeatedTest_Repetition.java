package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.UUID;

class RepeatedTest_Repetition {

    @RepeatedTest(10)
    void test_repeated() {
        Assertions.assertThat(UUID.randomUUID().toString()).hasSize(36);
    }

}
