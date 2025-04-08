package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

class TimeoutTest {

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void test_time() throws InterruptedException {
        Thread.sleep(900);
        Assertions.assertThat(true).isTrue();
    }

    @Disabled("Timeout 발생")
    @Test
    @Timeout(value = 2_100, unit = TimeUnit.MILLISECONDS)
    void test_timeout() throws InterruptedException {
        Thread.sleep(2_200);
        Assertions.assertThat(true).isTrue();
    }

}
