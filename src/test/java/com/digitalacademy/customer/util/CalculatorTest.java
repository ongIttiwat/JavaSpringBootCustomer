package com.digitalacademy.customer.util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void testAddMethod(){
        assertEquals(1, calculator.add(0,1));
    }

    @Test
    public void testMultiplyMethod(){
        assertEquals(0, calculator.multiply(0,1));
    }

    @Test
    public void testDivideMethod(){
        assertEquals(2, calculator.add(2,1));
    }

}
