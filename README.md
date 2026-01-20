# test-java

```shell
git switch study
```

## Testing

```shell
./gradlew test --rerun-tasks -i
```

```shell
# specify module
./gradlew :1-junit5:test --rerun-tasks -i
```

```shell
./gradlew slowTest --rerun-tasks -i
```

```shell
./gradlew slowTest --rerun-tasks --tests="file.*"
```

```shell
./gradlew slowTest --rerun-tasks --tests="*Test"
```

## Testcontainers

- macOS, Windows의 경우 Docker desktop 실행,
  Linux의 경우 Docker daemon 실행

```sh
./gradlew :3-testcontainers:test --rerun-tasks -i
```
