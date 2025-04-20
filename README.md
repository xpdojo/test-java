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
./gradlew :0-junit5:test --rerun-tasks -i
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
