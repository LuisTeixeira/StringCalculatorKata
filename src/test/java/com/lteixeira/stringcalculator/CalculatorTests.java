package com.lteixeira.stringcalculator;

import com.lteixeira.stringcaculator.Calculator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CalculatorTests {

    @Test
    public void emptyString() {
        // Setup
        String expression = "";
        int expected = 0;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Assert
        Assert.assertEquals(expected, result);

    }

    @Test
    public void singleNumber() {
        // Setup
        String expression = "1";
        int expected = 1;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addTwoNumbers() {
        // Setup
        String expression = "1,2";
        int expected = 3;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addUnknownAmountOfNumbers() {
        // Setup
        String expression = "1,2,30,2,65";
        int expected = 100;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

    @Test
    public void allowNewLineAsDelimiter() {
        // Setup
        String expression = "1\n2,3";
        int expected = 6;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

    @Test
    public void supportNewDelimiters() {

        // Setup
        String expression = "//;\n1;2";
        int expected = 3;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);

    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void negativesAreNotAllowed() {
        // Setup
        String expression = "1,-2,-30,2,65";
        String expectedMessage = "negatives not allowed - -2 -30";
        Calculator calculator = new Calculator();

        // Assert
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(expectedMessage);

        // Perform
        calculator.add(expression);
    }

    @Test
    public void ignoreNumberBigger1000() {
        // Setup
        String expression = "//;\n1001;2";
        int expected = 2;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

    @Test
    public void anyLengthDelimiter() {
        // Setup
        String expression = "//[***]\n1***2***3";
        int expected = 6;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

    @Test
    public void allowMultipleDelimiters() {
        // Setup
        String expression = "//[*][%]\n1*2%3";
        int expected = 6;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

    @Test
    public void allowMultipleAnyLengthDelimiters() {
        // Setup
        String expression = "//[***][%%]\n1***2%%3";
        int expected = 6;
        Calculator calculator = new Calculator();

        // Perform
        int result = calculator.add(expression);

        // Setup
        Assert.assertEquals(expected, result);
    }

}
