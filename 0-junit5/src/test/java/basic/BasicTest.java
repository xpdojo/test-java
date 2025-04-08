package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

@DisplayName("기능 테스트")
class BasicTest {

    @BeforeEach
    void setUp() {
        System.out.println("테스트 시작");
    }

    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_21)
    @Test
    @DisplayName("기능 테스트")
    void featureTest() {
        Assertions.assertThat(true).isTrue();
    }

    @Test
    @DisplayName("예외 테스트")
    void exceptionTest() {
        Assertions.assertThatThrownBy(() -> {
                    throw new RuntimeException("예외 발생 1");
                }).isInstanceOf(RuntimeException.class)
                .hasMessage("예외 발생 1");
    }

}
