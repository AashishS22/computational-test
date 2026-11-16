package testing.demo.computation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {

    private Calculator calculator;
    private static int testCount;

    @BeforeAll
    static void beforeAll() {
        testCount = 0;
        System.out.println(
            "[Before All] Calculator Test suite starting ... by ashah"
        );
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        calculator = new Calculator();
        testCount++;
        System.out.println(
            "\n[Before Each] Starting Test #" + testCount
            + ": " + testInfo.getDisplayName()
        );
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println(
            "[After Each] Finished Test #" + testCount
            + ": " + testInfo.getDisplayName()
        );
    }

    @AfterAll
    static void afterAll() {
        System.out.println(
            "\n[After All] completed " + testCount
            + " test invocations by ashah."
        );
    }

    static Stream<Arguments> additionData() {
        return Stream.of(
            Arguments.of(100, 2, 102),
            Arguments.of(100, -2, 98),
            Arguments.of(-100, 2, -98),
            Arguments.of(-100, -2, -102)
        );
    }

    @Order(1)
    @DisplayName("Add two numbers")
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @MethodSource("additionData")
    void testAdd(int first, int second, int expected) {
        assertEquals(expected, calculator.add(first, second));
    }

    @Order(2)
    @DisplayName("Subtract two numbers")
    @ParameterizedTest(name = "{0} - {1} = {2}")
    @CsvSource({
        "100, 2, 98",
        "100, -2, 102",
        "-100, 2, -102",
        "-100, -2, -98"
    })
    void testSubtract(int first, int second, int expected) {
        assertEquals(expected, calculator.substract(first, second));
    }

    @Order(3)
    @DisplayName("Multiply two numbers")
    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvFileSource(
        resources = "/multiplication.csv",
        numLinesToSkip = 1
    )
    void testMultiply(int first, int second, int expected) {
        assertEquals(expected, calculator.multiple(first, second));
    }

    @Order(4)
    @Test
    @DisplayName("Division by zero throws IllegalArgumentException")
    void testDivideByZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(100, 0)
        );

        assertEquals(
            "Denominator value cannot be zero.",
            exception.getMessage()
        );
    }
    @Order(5)
    @Test
    @DisplayName("100 / 2 = 50")
    void testDivideByNonzeroNumber() {
        assertEquals(50, calculator.divide(100, 2));
    }
}