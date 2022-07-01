package pl.training.goodweather.commons

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import java.lang.IllegalArgumentException

class CalculatorTest {

    private val calculator = Calculator()

    @Before
    fun beforeEach() {
        println("Before each")
    }

    @After
    fun afterEach() {
        println("After each")
    }

    @Test
    fun `given two numbers when add then returns their sum`() {
        // given
        val firstNumber = 1.0
        val secondNumber = 2.0
        // when
        val result = calculator.add(firstNumber, secondNumber)
        // then
        assertEquals(3.0, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given two numbers where divisor equals zero when add then throws exception`() {
        calculator.divide(10.0, 0.0)
    }

}