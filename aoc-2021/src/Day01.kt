private val testInput = readInput("Day01_test")
private val realInput = readInput("Day01")

private fun part1(input: List<String>): Int {
    val inputAsNumbers = input.map { it.toInt() }
    var lastValue = inputAsNumbers[0]
    var count = 0
    inputAsNumbers.forEach {
        val newValue = it
        if (newValue > lastValue) {
            count++
        }
        lastValue = newValue
    }
    return count
}

private fun part2(input: List<String>): Int {
    val inputAsNumbers = input.map { it.toInt() }
    val lastWindow = MutableList(3) { inputAsNumbers[it] }
    var count = 0
    inputAsNumbers.drop(3).forEachIndexed { index, value ->
        val newWindow = MutableList(3) { inputAsNumbers[index + it + 1] }
            if (newWindow.sum() > lastWindow.sum()) {
            count++
        }
        lastWindow[0] = lastWindow[1]
        lastWindow[1] = lastWindow[2]
        lastWindow[2] = value
    }
    return count
}

fun main() {
    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}