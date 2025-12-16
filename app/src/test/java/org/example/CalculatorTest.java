package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    
    @Test
    void testAddNumbers(){
        Calculator calc = new Calculator();
        int result = calc.add(2, 3);
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    void testSubtractNumbers(){
        Calculator calc = new Calculator();
        int result = calc.substract(5, 3);
        assertEquals(2, result, "5 - 3 should equal 2");
    }

    @Test
    void testMultiplyNumbers(){
        Calculator calc = new Calculator();
        int result = calc.multiply(4, 3);
        assertEquals(12, result, "4 * 3 should equal 12");
    }

    @Test
    void testDivideNumbers(){
        Calculator calc = new Calculator();
        int result = calc.divide(10, 2);
        assertEquals(5, result, "10 / 2 should equal 5");
    }

    @Test
    void testDivideByZero(){
        Calculator calc = new Calculator();
        Exception exception = assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
        assertEquals("Division by zero is not allowed.", exception.getMessage());
    }

    @Test
    void testModuloNumbers(){
        Calculator calc = new Calculator();
        int result = calc.modulo(10, 3);
        assertEquals(1, result, "10 % 3 should equal 1");
    }

    @Test
    void testModuloByZero(){
        Calculator calc = new Calculator();
        Exception exception = assertThrows(ArithmeticException.class, () -> calc.modulo(10, 0));
        assertEquals("Modulo by zero is not allowed.", exception.getMessage());
    }

    @Test
    void testSquareRootPositive(){
        Calculator calc = new Calculator();
        double result = calc.squareRoot(16.0);
        assertEquals(4.0, result, 0.0001);
    }

    @Test
    void testSquareRootNegative(){
        Calculator calc = new Calculator();
        Exception exception = assertThrows(ArithmeticException.class, () -> calc.squareRoot(-4.0));
        assertEquals("Cannot calculate square root of negative number.", exception.getMessage());
    }

    @Test
    void testPower(){
        Calculator calc = new Calculator();
        double result = calc.power(2, 3);
        assertEquals(8.0, result, 0.0001);
    }

    @Test
    void testAbsoluteValuePositive(){
        Calculator calc = new Calculator();
        int result = calc.absoluteValue(5);
        assertEquals(5, result);
    }

    @Test
    void testAbsoluteValueNegative(){
        Calculator calc = new Calculator();
        int result = calc.absoluteValue(-7);
        assertEquals(7, result);
    }
}
