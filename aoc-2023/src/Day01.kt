private val testInput = readInput("Day01_test")
private val realInput = readInput("Day01")

val wordToNumberMap = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun sumCalibrationValues(input: List<String>, regex: Regex): Int {
    var counter = 0
    for (line in input) {
        val numbers = mutableListOf<String>()
        for (i in line.indices)  {
            val lineSlice = line.slice(i until line.length)
            val number = regex.find(lineSlice)?.value
            if (number != null) {
                numbers.add(number)
            }
        }
        val converted = numbers.map { wordToNumberMap[it] ?: it.toInt() }
        val valueToAdd = "${converted.first()}${converted.last()}".toInt()
        counter += valueToAdd
    }
    return counter
}

fun main() {
    fun part1(input: List<String>): Int {
        return sumCalibrationValues(input, Regex("""\d"""))
    }

    fun part2(input: List<String>): Int {
        return sumCalibrationValues(input, Regex("""\d|one|two|three|four|five|six|seven|eight|nine""", ))
    }

//    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}
