package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParameterizedTest_ValueSource {

    @DisplayName("ValueSource: 단일 타입의 파라미터 (string)")
    @ParameterizedTest
    @ValueSource(strings = {"apple", "banana", "kiwi"})
    void testWithStringParameter(String fruit) {
        Assertions.assertThat(fruit).containsAnyOf("a", "n", "k");
        Assertions.assertThat(fruit).doesNotContain("x", "z");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testWithIntegerParameter(Integer integer) {
        Assertions.assertThat(integer).isIn(1, 2, 3);
        Assertions.assertThat(integer).isNotIn(4, 5, 6);
    }

}
