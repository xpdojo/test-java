# JUnit 5 Example

```shell
./gradlew clean test
```

## JUnit 5의 대표적인 API (주로 JUnit Jupiter 기준)

JUnit 5는 모듈식 구조(JUnit Platform, JUnit Jupiter, JUnit Vintage)를 가지며,
개발자가 주로 사용하는 API는 **JUnit Jupiter** 엔진에 포함되어 있습니다.
다음은 JUnit 5에서 자주 사용되는 대표적인 API(어노테이션 및 클래스/메소드)입니다.

### 1. 기본 테스트 어노테이션

- `@Test`: 해당 메소드가 테스트 메소드임을 나타냅니다. JUnit 4와 달리 `public`일 필요가 없습니다.
- `@DisplayName`: 테스트 클래스나 메소드의 사용자 정의 이름을 지정합니다. 테스트 결과 리포트에서 가독성을 높여줍니다.
- `@Disabled`: 특정 테스트 클래스나 메소드를 비활성화합니다. 실행되지 않도록 할 때 사용합니다.

### 2. 테스트 생명주기 콜백 (Lifecycle Callbacks)

- `@BeforeAll`: 해당 클래스의 모든 테스트 메소드 실행 *전에* 한 번 실행됩니다. `static` 메소드여야 합니다 (단, `@TestInstance(Lifecycle.PER_CLASS)` 사용 시에는 non-static 가능).
- `@AfterAll`: 해당 클래스의 모든 테스트 메소드 실행 *후에* 한 번 실행됩니다. `static` 메소드여야 합니다 (단, `@TestInstance(Lifecycle.PER_CLASS)` 사용 시에는 non-static 가능).
- `@BeforeEach`: 각 테스트 메소드 실행 *전에* 실행됩니다.
- `@AfterEach`: 각 테스트 메소드 실행 *후에* 실행됩니다.

### 3. 단언 (Assertions)

- `org.junit.jupiter.api.Assertions` 클래스: 테스트 결과를 검증하는 다양한 `static` 메소드를 제공합니다.
  - `assertEquals(expected, actual)`: 두 값이 같은지 확인합니다.
  - `assertTrue(boolean condition)`: 조건이 참인지 확인합니다.
  - `assertFalse(boolean condition)`: 조건이 거짓인지 확인합니다.
  - `assertNotNull(Object object)`: 객체가 null이 아닌지 확인합니다.
  - `assertNull(Object object)`: 객체가 null인지 확인합니다.
  - `assertSame(expected, actual)`: 두 객체가 동일한 객체(메모리 주소)인지 확인합니다.
  - `assertNotSame(unexpected, actual)`: 두 객체가 동일한 객체가 아닌지 확인합니다.
  - `assertThrows(expectedType, executable)`: 특정 예외가 발생하는지 확인합니다. 발생한 예외 객체를 반환받아 추가 검증도 가능합니다.
  - `assertAll(executables...)`: 여러 개의 단언을 그룹화하여 실행합니다. 그룹 내 하나의 단언이 실패해도 나머지 단언을 계속 실행합니다.
  - `fail()`: 테스트를 무조건 실패시킵니다.

### 4. 테스트 그룹화 및 조건부 실행

- `@Tag`: 테스트에 태그를 지정하여 특정 태그가 붙은 테스트만 실행하거나 제외할 수 있습니다. 빌드 도구나 IDE에서 활용됩니다.
- `@Nested`: 중첩 클래스를 이용하여 테스트를 계층적으로 구조화하고 그룹화할 수 있습니다. 내부 클래스는 non-static 이어야 합니다.

### 5. 파라미터화 테스트 (Parameterized Tests)

- `@ParameterizedTest`: 동일한 테스트 로직을 다른 파라미터 값으로 여러 번 실행할 때 사용합니다.
- Source Annotations (데이터 소스 지정):
  - `@ValueSource`: 기본 타입(String, int, long, double 등)의 배열을 파라미터로 제공합니다.
  - `@CsvSource`: 쉼표로 구분된 값(CSV)을 파라미터로 제공합니다.
  - `@CsvFileSource`: CSV 파일로부터 파라미터를 읽어옵니다.
  - `@MethodSource`: 팩토리 메소드가 반환하는 Stream, Collection, Iterator 등을 파라미터로 사용합니다.
  - `@EnumSource`: Enum 상수를 파라미터로 사용합니다.
  - `@ArgumentsSource`: `ArgumentsProvider` 구현체를 통해 파라미터를 제공합니다.

### 6. 반복 테스트 (Repeated Tests)

- `@RepeatedTest(int value)`: 지정된 횟수만큼 테스트 메소드를 반복 실행합니다. `RepetitionInfo`를 파라미터로 받아 현재 반복 횟수 등을 알 수 있습니다.

### 7. 테스트 인스턴스 생명주기

- `@TestInstance(Lifecycle.PER_CLASS)`: 테스트 클래스 인스턴스를 클래스 단위로 하나만 생성하도록 설정합니다.
  - 기본값은 `PER_METHOD` (각 테스트 메소드마다 새 인스턴스 생성)입니다.
  - `PER_CLASS`로 설정하면 `@BeforeAll`, `@AfterAll` 메소드가 `static`이 아니어도 됩니다.

### 8. 테스트 순서 지정

- `@TestMethodOrder`: 테스트 메소드의 실행 순서를 지정하는 전략을 선택합니다.
  - `MethodOrderer.OrderAnnotation`: `@Order` 어노테이션으로 순서를 지정합니다.
  - `MethodOrderer.DisplayName`: `@DisplayName` 기준으로 정렬합니다.
  - `MethodOrderer.MethodName`: 메소드 이름 알파벳 순으로 정렬합니다.
- `MethodOrderer.Random`: 무작위 순서로 실행합니다.
- `@Order(int value)`: `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`와 함께 사용하여 테스트 메소드의 실행 순서를 정수 값으로 지정합니다 (낮은 값이 먼저 실행).

이 API들은 JUnit 5를 사용하여 효과적이고 구조화된 테스트 코드를 작성하는 데 핵심적인 역할을 합니다. 필요에 따라 다양한 API를 조합하여 테스트 요구사항을 충족시킬 수 있습니다.
