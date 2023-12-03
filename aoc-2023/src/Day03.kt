private val testInput = readInput("Day03_test")
private val realInput = readInput("Day03")

fun parseEngine(input: List<String>): List<List<String>> {
    return input.map { line -> line.split("").filter { it != "" } }
}

val symbols = listOf("#", "@", "*", "+", "-", "=", "&", "/", "%", "$", "!", "?")

fun main() {
    fun part1(input: List<String>): Int {
        val lineLength = input[0].length
        val inputHeight = input.size
        val engine = parseEngine(input)

        fun indexBordersSymbol(lineIndex: Int, index: Int): Boolean {
            val toCheck = listOf(
                Coordinate(lineIndex - 1, index - 1),
                Coordinate(lineIndex - 1, index),
                Coordinate(lineIndex - 1, index + 1),
                Coordinate(lineIndex, index - 1),
                Coordinate(lineIndex, index),
                Coordinate(lineIndex, index + 1),
                Coordinate(lineIndex + 1, index - 1),
                Coordinate(lineIndex + 1, index),
                Coordinate(lineIndex + 1, index + 1),
            )
            return toCheck.any { coordinate ->
                if (coordinate.x < 0 || coordinate.x >= lineLength || coordinate.y < 0 || coordinate.y >= inputHeight) {
                    return@any false
                }
                if (engine[coordinate.x][coordinate.y] in symbols) {
                    return@any true
                }
                return@any false
            }
        }
        fun rangeBordersSymbol(lineIndex: Int, range: IntRange): Boolean {
            return range.any { indexBordersSymbol(lineIndex, it) }
        }

        var counter = 0
        input.forEachIndexed { index, line ->
            val numbersRegexResult = Regex("""\d+""").findAll(line)
            numbersRegexResult.forEach { number ->
                if (rangeBordersSymbol(index, number.range)) {
                    counter += number.value.toInt()
                }
            }
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}