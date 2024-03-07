package com.sedefbas.learnTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;



public class SimpleTestClass {
    Calculator calculatorTest = new Calculator();
    class Calculator{
        int add(int a, int b){
            return a + b;
        }
        int multiply(int a, int b){
            return a * b;
        }

        int divide(int a, int b){
            return a / b;
        }
    }
   //todo test1
    @Test
    @DisplayName(value = "This test should return equals when add two number") //method ismi değiştirme
    @RepeatedTest(3) //testi 3 kere çalıştırır
    public void should_returnEquals_when_addTwoNumber(){
        // given
        int firstNumber = 10;
        int secondNumber = 20;
        int expected = 30;

        // when
        int actual = calculatorTest.add(firstNumber, secondNumber);

        // then
        Assertions.assertEquals(expected, actual);
    }

    //todo test2
    @Test
    public void should_returnNotEquals_when_addTwoNumber(){
        // given
        int firstNumber = 10;
        int secondNumber = 20;
        int expected = 40;

        // when
        int actual = calculatorTest.add(firstNumber, secondNumber);

        // then
        Assertions.assertNotEquals(expected, actual);
    }


    //todo test3
    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 0, 20})
    public void should_returnZero_when_multiplyNumberWithZero(int givenSource){
        // given
        int firstNumber = givenSource;
        int secondNumber = 0;

        // when
        int actual = calculatorTest.multiply(firstNumber, secondNumber);

        // then
        Assertions.assertTrue(actual == 0);
    }

    //todo test4
    @ParameterizedTest(name = "1st={0}, 2nd={1}")
    @CsvSource(value = {"-10, -1", "-10, -20", "-3, -45"}) //birden fazla değer girmek için kullanılır.
    public void should_returnTrue_when_multiplyTwoNegativeNumbers(int givenFirstNumber, int givenSecondNumber){
        // given
        int firstNumber = givenFirstNumber;
        int secondNumber = givenSecondNumber;

        // when
        int actual = calculatorTest.multiply(firstNumber, secondNumber);

        // then
        Assertions.assertTrue(actual > 0);
    }

    //todo test5
    //hata sarmalamayı öğrendik.
    @Test
    public void should_throwException_when_divideNumberWithZero(){
        // given
        int firstNumber = 10;
        int secondNumber = 0;

        // when
        Executable executable = () -> calculatorTest.divide(firstNumber, secondNumber);

        // then
        Assertions.assertThrows(ArithmeticException.class, executable);
    }
}
