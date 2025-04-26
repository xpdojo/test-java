package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GroupingNested {

    @Nested
    class Describe_A {
        @Test
        void test() {
            Assertions.assertThat("a").isNotEqualTo("b");
        }
    }

    @Nested
    class Describe_B {
        @Nested
        class Describe_C {
            @Test
            void test() {
                Assertions.assertThat("a").isNotEqualTo("b");
            }
        }

        @Test
        void test() {
            Assertions.assertThat("a").isNotEqualTo("b");
        }
    }

}
