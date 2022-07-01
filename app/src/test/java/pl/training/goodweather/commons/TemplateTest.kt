package pl.training.goodweather.commons

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException

class TemplateTest {

    private val textWithoutExpressions = "My name is Jan Kowalski"
    private val textWithExpressions = "My name is #{firstName} #{lastName}"

    @Test
    fun `given a text without expressions when evaluate then returns the text`() {
        val template = Template(textWithoutExpressions)
        val result = template.evaluate(emptyMap())
        assertEquals(textWithoutExpressions, result)
    }

    @Test
    fun `given a text with expressions when evaluate then returns the text with substituted parameters`() {
        val template = Template(textWithExpressions)
        val parameters = mapOf("firstName" to "Jan", "lastName" to "Kowalski")
        val result = template.evaluate(parameters)
        assertEquals(textWithoutExpressions, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given a text with expressions when evaluate without providing all parameters then throws an exception`() {
        Template(textWithExpressions).evaluate(emptyMap())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given a text with expressions when evaluate with non alphanumeric parameters then throws an exception`() {
        val parameters = mapOf("firstName" to "@", "lastName" to "Kowalski")
        Template(textWithExpressions).evaluate(parameters)
    }

    @Test
    fun `given a text with expressions when evaluate with additional parameters then returns the text with substituted parameters`() {
        val template = Template(textWithExpressions)
        val parameters = mapOf("firstName" to "Jan", "lastName" to "Kowalski", "age" to "24")
        val result = template.evaluate(parameters)
        assertEquals(textWithoutExpressions, result)
    }

}