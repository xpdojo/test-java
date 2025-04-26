package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ParameterizedTest_EnumSource {

    @DisplayName("EnumSource: enum 타입의 파라미터")
    @ParameterizedTest
    @EnumSource(Fruit.class)
    void testWithEnumParameter(Fruit fruit) {
        Assertions.assertThat(fruit).isIn(
                Fruit.APPLE,
                Fruit.BANANA,
                Fruit.KIWI
        );
    }

    @DisplayName("Mode.INCLUDE: names에 포함된 enum만 테스트")
    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.INCLUDE, names = {"APPLE", "BANANA"})
    void testWithEnumParameter_isIn(Fruit fruit) {
        Assertions.assertThat(fruit).isIn(
                Fruit.APPLE,
                Fruit.BANANA
        );
    }

    public enum Fruit {
        APPLE,
        BANANA,
        KIWI
    }

}
