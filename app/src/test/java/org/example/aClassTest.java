package org.example;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.*;

@Epic("Calculator Functionality")
@Feature("Basic Arithmetic Operations")
public class aClassTest {
    
    private Calculator calc;

    @BeforeEach
    void setup(){
        // Setup code if needed
        calc = new Calculator();
    }

    @Test
    @Tag("critical")
    @Story("Subtraction")
    @Description("Verify that subtraction works correctly with positive numbers")
    @Severity(SeverityLevel.CRITICAL)
    void testDeleteNumbers(){
        assertEquals(1, calc.substract(3, 2), "3 - 2 should equal 1");
    }

    @Test
    @Tag("critical")
    @Story("Addition")
    @Description("Verify that two positive numbers are added correctly")
    @Severity(SeverityLevel.CRITICAL)
    void testAddNumbers(){
        int result = calc.add(2, 3);
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    @Story("Addition")
    @Description("Verify that negative numbers are added correctly")
    @Severity(SeverityLevel.NORMAL)
    void testAddNegativeNumbers(){
        int result = calc.add(-2,-3);
        assertEquals(-5, result, "Expected value: -5 and Actual Value: " + result);
    }

    @Test
    @Story("Addition")
    @Description("Verify that adding zero returns the original number (edge case)")
    @Severity(SeverityLevel.NORMAL)
    void testZeroAddition(){
        int result = calc.add(10,0);
        assertEquals(10, result, "Adding zero should return the same number");
    }

    @Test
    @Story("Addition")
    @Description("Verify that large numbers are added correctly without overflow")
    @Severity(SeverityLevel.MINOR)
    void testLargeNumbersAddition(){
        int result = calc.add(1000000,2000000);
        assertEquals(3000000, result, "Expected value: 3000000 and Actual Value: " + result);
    }

    @Test
    @Story("Multiplication")
    @Description("Verify that two positive numbers are multiplied correctly")
    @Severity(SeverityLevel.CRITICAL)
    void testMultiplyPositiveNumbers(){
        int result = calc.multiply(2, 3);
        assertEquals(6, result, "2 * 3 should equal 6");
    }

    @Test
    @Story("Multiplication")
    @Description("Verify that multiplying by zero returns zero")
    @Severity(SeverityLevel.NORMAL)
    void testMultiplyWithZero(){
        int result = calc.multiply(2, 0);
        assertEquals(0, result, "2 * 0 should equal 0");
    }

    @Test
    @Story("Multiplication")
    @Description("Verify that two negative numbers are multiplied correctly")
    @Severity(SeverityLevel.CRITICAL)
    void testMultiplyNegativeNumbers(){
        int result = calc.multiply(-2, -4);
        assertEquals(8, result, "-2 * -4 should equal 8");
    }


    @Test
    @Story("division")
    @Description("Verify that dividing two positive numbers works correctly")
    @Severity(SeverityLevel.CRITICAL)
    void testDividePositiveNumbers(){
        int result = calc.divide(6, 3);
        assertEquals(2, result, "6 / 3 should equal 2");
    }

    @Test
    @Story("division")
    @Description("Verify that dividing by zero throws an exception")
    @Severity(SeverityLevel.BLOCKER)
    void testDivideByZero(){
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(6, 0);
        }, "Dividing by zero should throw ArithmeticException");
    }


    @Test
    @Tag("critical")
    @Story("Modulo Operation")
    @Description("Verify that modulo operation returns correct remainder")
    @Severity(SeverityLevel.NORMAL)
    void testModuloOperation(){
        assertEquals(1, calc.modulo(10, 3), "10 % 3 should equal 1");
        assertEquals(0, calc.modulo(10, 5), "10 % 5 should equal 0");
        assertEquals(2, calc.modulo(17, 5), "17 % 5 should equal 2");
    }

    @Test
    @Tag("critical")
    @Story("Modulo Operation")
    @Description("Verify that modulo by zero throws ArithmeticException")
    @Severity(SeverityLevel.CRITICAL)
    void testModuloByAZero(){
        assertThrows(ArithmeticException.class, () -> calc.modulo(6, 0) ,"Modulo by zero should throw ArithmeticException");
    }


    @AfterEach
    void teardown(){
        // Teardown code if needed
    }
}
