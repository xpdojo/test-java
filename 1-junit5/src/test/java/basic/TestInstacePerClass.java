package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * default: Lifecycle.PER_METHOD는 각 테스트 메소드마다 새로운 인스턴스를 생성한다.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestInstacePerClass {

    private int count = 0;

    @BeforeAll
    void setUp() {
        count = 1;
    }

    @Test
    void test1() {
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    void test2() {
        Assertions.assertThat(count).isEqualTo(1);
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class NestedTest {
        private int count = 0;

        @BeforeAll
        void setUp() {
            count = 2;
        }

        @Test
        void test3() {
            Assertions.assertThat(count).isEqualTo(2);
        }

        @Nested
        class NestedTest2 {
            private int count = 0;

            @Test
            void test4() {
                Assertions.assertThat(count).isZero();
            }
        }
    }

}
