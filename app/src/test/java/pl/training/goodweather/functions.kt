package pl.training.goodweather

import org.junit.Test
import pl.training.goodweather.Option.None
import pl.training.goodweather.Option.Some
import kotlin.math.log

fun abs(value: Int) = if (value < 0) -value else value

fun add(a: Int, b: Int) = a + b

//fun add(a: Int): (Int) -> (Int) {
//    return { b -> a + b }
//}

fun factorial(number: Int): Int {
    tailrec fun loop(n: Int, result: Int = 1): Int = if (n < 0) result else loop(n - 1, n * result)
    return loop(number)
}

fun formatResult(value: Int, f: (Int) -> (Int)) = "Result for $value equals ${f(value)}"

typealias Predicate<E> = (E) -> Boolean

fun <E> findFirst(xs: Array<E>, predicate: Predicate<E>): Int {
    tailrec fun loop(index: Int): Int = when {
        index == xs.size -> -1
        predicate(xs[index]) -> index
        else -> loop(index + 1)
    }
    return loop(0)
}

fun <A, B, C> partial(a: A, f: (A, B) -> C): (B) -> C = { b -> f(a, b) }
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = {  a -> f(g(a)) }

sealed class Option<out A> {
    data class Some<A>(val value: A): Option<A>()
    object None: Option<Nothing>()
}

fun <A, B> Option<A>.map(f: (A) -> B) = when(this) {
    is None -> None
    is Some -> Some(f(value))
}

fun <A> Option<A>.getOrElse(default: () -> A): A = when(this) {
    is None -> default()
    is Some -> value
}

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = map(f).getOrElse { None }

sealed class Either<out E, out A>
data class Left<out E>(val value: E): Either<E, Nothing>()
data class Right<out A>(val value: A): Either<Nothing, A>()

fun safeDiv(x: Int, y: Int): Either<String, Int> =
    try {
        Right(x / y)
    } catch (e: Exception) {
        Left("Division by zero")
    }

data class User(val name: String, val email: Option<String>)

sealed class List<out A> {

    companion object {

        fun <A> of(vararg xs: A): List<A> {
            val tail = xs.sliceArray(1 until xs.size)
            return if (xs.isEmpty()) Nil else Cons(xs[0], of(*tail))
        }

    }

}
object Nil: List<Nothing>()
data class Cons<out A>(val head: A, val tail: List<A>): List<A>()

fun sum(xs: List<Int>): Int = when(xs) {
    is Nil -> 0
    is Cons -> xs.head + sum(xs.tail)
}

fun product(xs: List<Int>): Int = when(xs) {
    is Nil -> 1
    is Cons -> if (xs.head == 0) 0 else xs.head * product(xs.tail)
}

fun <A, B> reduce(xs: List<A>, value: B, f: (A, B) -> B): B = when(xs) {
    is Nil -> value
    is Cons -> f(xs.head, reduce(xs.tail, value, f))
}

fun sum2(xs: List<Int>) = reduce(xs, 0) { a, b -> a + b }
fun product2(xs: List<Int>) = reduce(xs, 1) { a, b -> a * b }


fun fahrenheitToCelsius(value: Double): Double = (value - 32) * 5.0 / 9.0
fun toFixed(value: Double) = String.format("%.2f", value)
val convertAndFormat = compose(::toFixed, ::fahrenheitToCelsius)
fun formatResult(temperature: String) = "Temperature equals $temperature"

fun write(text: String): IO<Unit> = IO { println(text) }
fun read(): IO<String> = IO { readLine().orEmpty() }

interface IO<A> {

    fun run(): A

    fun <B> map(f: (A) -> B): IO<B> = object : IO<B> {

        override fun run(): B = f(this@IO.run())

    }

    fun <B> flatMap(f: (A) -> IO<B>): IO<B> = object : IO<B> {

        override fun run(): B = f(this@IO.run()).run()

    }

    companion object {

        fun <A> unit(a: () -> A) = object : IO<A> {

            override fun run(): A = a()

        }

        operator fun <A> invoke(a: () -> A) = unit(a)

    }

}

fun main() {
    write("Enter temperature in degrees Fahrenheit")
        .flatMap { read() }
        .map { it.toDouble() }
        .map { convertAndFormat(it) }
        .map { formatResult(it) }
        .flatMap { write(it) }
        .run()
}

class Tests {

    fun test() {
        // val sum = add(3)(2)
        val sum = partial(3, ::add)(2)
        val value = -1
        println(abs(value) == if (value < 0) -value else value)
        println(formatResult(4, ::factorial))
        println(formatResult(-4, ::abs))
        val numbers = arrayOf(1, 2, 3, 4, 5)
        println(findFirst(numbers) { it > 2 })

        val sum3 = partial(3, ::add)
        // sum3(abs(-5))
        val absSum3 = compose(sum3, ::abs)
        val user: Option<String> = None // Some("Jan")

        println(
            user.map { it.uppercase() }
                .getOrElse { "Unknown" }
        )

        val defaultUser = Some(User("Jan Kowalski", Some("jan@training.pl")))
        println(defaultUser.map { it.email })
        println(defaultUser.flatMap { it.email })

        val emptyList = Nil
        val names = List.of("Jan", "Maria") // Cons("Jan", Cons("Maria", Nil))
        val values = List.of(1, 2, 3, 4, 5)
        println(sum(values))
        println(sum2(values))
        println(product(values))
        println(product2(values))
    }



}
