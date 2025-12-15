package org.example;

public final class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int substract(int a, int b) {
        return a - b;
    }

     public int multiply(int a, int b){
        return a*b;
    }

    public int divide(int a, int b){
        if(b==0){
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a/b;
    }

    public int modulo(int a, int b){
        if(b==0){
            throw new ArithmeticException("Modulo by zero is not allowed.");
        }
        return a % b;
    }

    // Main branch: Adding square root operation
    public double squareRoot(double number){
        if(number < 0){
            throw new ArithmeticException("Cannot calculate square root of negative number.");
        }
        return Math.sqrt(number);
    }
}
