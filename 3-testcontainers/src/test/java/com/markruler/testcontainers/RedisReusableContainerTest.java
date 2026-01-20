package com.markruler.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class RedisReusableContainerTest {

    @SuppressWarnings("resource")
    @Container
    public static GenericContainer<?> redis = new GenericContainer<>("redis:7.2-alpine")
            .withExposedPorts(6379)
            .withReuse(true);

    private static String firstContainerId;

    @Test
    @Order(1)
    void incrementCounter1() {
        String redisUri = "redis://" + redis.getHost() + ":" + redis.getMappedPort(6379);
        try (RedisClient client = RedisClient.create(redisUri);
             StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            Long first = commands.incr("counter");
            assertThat(first).isEqualTo(1L);
        }
        firstContainerId = redis.getContainerId();
    }

    @Test
    @Order(2)
    void incrementCounter2() {
        String redisUri = "redis://" + redis.getHost() + ":" + redis.getMappedPort(6379);
        try (RedisClient client = RedisClient.create(redisUri);
             StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            Long second = commands.incr("counter");
            assertThat(second).isEqualTo(2L);
        }
    }

    @Test
    @Order(3)
    void reuseContainerAcrossTests() {
        assertThat(firstContainerId).isNotNull();
        assertThat(redis.getContainerId()).isEqualTo(firstContainerId);
    }

}
