package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * <pre>
 *      ./gradlew integrationTest
 * </pre>
 *
 * @see <a href="{@docRoot}/build.gradle">integrationTest task</a>
 */
class GroupingTag_integrationTest {

    @Tag("slow")
    @Test
    void slow() throws InterruptedException {
        // slow test
        Thread.sleep(3_000);
        Assertions.assertThat(Thread.currentThread().getName()).isNotEqualTo("a");
    }

}
