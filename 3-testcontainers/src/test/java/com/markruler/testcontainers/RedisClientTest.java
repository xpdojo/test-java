package com.markruler.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

class RedisClientTest {

    @Test
    void setAndGetValue() {
        withRedis(commands -> {
            commands.set("greeting", "hello");
            String value = commands.get("greeting");

            assertThat(value).isEqualTo("hello");
        });
    }

    @Test
    void incrementCounter() {
        withRedis(commands -> {
            Long count = commands.incr("counter");
            assertThat(count).isEqualTo(1L);
        });
    }

    private void withRedis(java.util.function.Consumer<RedisCommands<String, String>> action) {
        GenericContainer<?> redis = new GenericContainer<>("redis:7.2-alpine");
        redis.withExposedPorts(6379);
        try (redis) {
            redis.start();
            String redisUri = "redis://" + redis.getHost() + ":" + redis.getMappedPort(6379);

            try (RedisClient client = RedisClient.create(redisUri);
                 StatefulRedisConnection<String, String> connection = client.connect()) {
                RedisCommands<String, String> commands = connection.sync();
                action.accept(commands);
            }
        }
    }

}
