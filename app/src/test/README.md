# DailyPlanet News App - Unit Tests

## Overview
This directory contains unit tests for the DailyPlanet News App. The tests are designed to validate the core business logic and data models without requiring the Android framework or emulator.

## Test Structure

### BasicUnitTest.kt
Basic infrastructure tests to verify that the test environment is working correctly.
- Addition and string operations
- List operations
- Nullable type handling

### Models Tests
#### ArticleTest.kt
Tests for the `Article` data class:
- Creation with all fields
- Creation with nullable fields
- Equality and copy operations
- Edge cases (empty strings, etc.)

#### SourceTest.kt
Tests for the `Source` data class:
- Creation with all fields and null id
- Equality comparisons
- Copy operations
- Edge cases

#### NewsResponseTest.kt
Tests for the `NewsResponse` data class:
- Creation with articles
- Empty articles list
- Error status handling
- Data integrity validation

### Database Tests
#### ConvertersTest.kt
Tests for Room database type converters:
- `fromSource()` - Convert Source to String
- `toSource()` - Convert String to Source
- Roundtrip conversion validation
- Special character handling

### API Tests
#### ConstantsTest.kt
Tests for API configuration constants:
- API key validation
- Base URL format validation
- HTTPS enforcement
- URL structure validation

## Running the Tests

### Using Gradle
Run all unit tests:
```bash
./gradlew test
```

Run tests with detailed output:
```bash
./gradlew test --info
```

Run tests for a specific variant:
```bash
./gradlew testDebugUnitTest
```

### Using Android Studio
1. Right-click on the `test` directory
2. Select "Run 'All Tests'"

Or run individual test files:
1. Open a test file
2. Click the green play button in the gutter
3. Select "Run [TestName]"

## Test Coverage
Total test methods: **35 tests**
- BasicUnitTest: 4 tests
- ArticleTest: 6 tests
- SourceTest: 6 tests
- NewsResponseTest: 7 tests
- ConvertersTest: 9 tests
- ConstantsTest: 6 tests

## Testing Conventions
- Test names use backticks for readability: `` `test description` ``
- Tests follow the Arrange-Act-Assert (AAA) pattern
- Each test validates a single behavior or scenario
- Tests are independent and can run in any order

## Dependencies
The tests use:
- JUnit 4 for test framework
- Standard Kotlin assertions
- No mocking frameworks (tests are for pure Kotlin logic)

## Future Improvements
Consider adding:
- Repository tests (with mocking)
- ViewModel tests (with mocking)
- Integration tests for API and database
- UI tests in androidTest directory
