import kotlin.math.abs

private val testInput = readInput("Day07_test")
private val realInput = readInput("Day07")

private fun triangle(n: Int): Int {
    val nPlusOne = n + 1
    return n * nPlusOne / 2
}

fun main() {
    fun part1(input: List<String>): Int {
        val xValues = input[0].split(',').map { it.toInt() }.sorted()
        val min = xValues.min()
        val max = xValues.max()

        return (min..max).fold(Int.MAX_VALUE) { minTotal, x ->
            val totalDistance = xValues.fold(0) { acc, i ->
                acc + abs(x - i)
            }
            if(totalDistance < minTotal) totalDistance else minTotal
        }
    }

    fun part2(input: List<String>): Int {
        val xValues = input[0].split(',').map { it.toInt() }.sorted()
        val min = xValues.min()
        val max = xValues.max()

        return (min..max).fold(Int.MAX_VALUE) { minTotal, x ->
            val totalDistance = xValues.fold(0) { acc, i ->
                acc + triangle(abs(x - i))
            }
            if(totalDistance < minTotal) totalDistance else minTotal
        }
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}