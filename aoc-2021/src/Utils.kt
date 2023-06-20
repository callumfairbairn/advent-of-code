import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.floor

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Alphabetizes string.
 */
fun String.alphabetized() = String(toCharArray().apply { sort() })

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

// Coordinate data class
data class Coordinate(val x: Int, val y: Int)

// transpose Array
inline fun <reified T> transpose(xs: Array<Array<T>>): Array<Array<T>> {
    val cols = xs[0].size
    val rows = xs.size
    return Array(cols) { j ->
        Array(rows) { i ->
            xs[i][j]
        }
    }
}

// transpose List
inline fun <reified T> transpose(xs: List<List<T>>): List<List<T>> {
    val cols = xs[0].size
    val rows = xs.size
    return List(cols) { j ->
        List(rows) { i ->
            xs[i][j]
        }
    }
}

// product of List
fun List<Int>.product(): Int {
    return reduce { acc, i -> acc * i }
}

// middle of list
fun List<BigInteger>.middle(): BigInteger {
    return this[floor(size / 2.00).toInt()]
}

// count up or down e.g. 1 toward 100 or 100 toward 1
infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}
