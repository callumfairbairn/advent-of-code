private val testInput = readInput("Day03_test")
private val realInput = readInput("Day03")

fun parseEngine(input: List<String>): List<List<String>> {
    return input.map { line -> line.split("").filter { it != "" } }
}

val symbols = listOf("#", "@", "*", "+", "-", "=", "&", "/", "%", "$", "!", "?")

class Engine(val input: List<String>) {
    private val lineLength = input[0].length
    private val inputHeight = input.size
    private val engine = parseEngine(input)

    fun getSymbol(coord: Coordinate): String {
        return engine[coord.x][coord.y]
    }

    private fun symbolCoordBorderingIndex(lineIndex: Int, index: Int): Coordinate? {
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
        toCheck.forEach { coordinate ->
            val canCheck = !(coordinate.x < 0 || coordinate.x >= lineLength || coordinate.y < 0 || coordinate.y >= inputHeight)
            if (canCheck && engine[coordinate.x][coordinate.y] in symbols) {
                return coordinate
            }
        }
        return null
    }

    private fun symbolCoordBorderingRange(lineIndex: Int, range: IntRange): Coordinate? {
        range.forEach { index ->
            val symbol = symbolCoordBorderingIndex(lineIndex, index)
            if (symbol != null) {
                return symbol
            }
        }
        return null
    }

    fun getNumbersAndAdjacentSymbolCoordinates(onlyIncludeGears: Boolean = false): List<Pair<Int, Coordinate>> {
        val numbersAndCoordinate = mutableListOf<Pair<Int, Coordinate>>()
        input.forEachIndexed { index, line ->
            val numbersRegexResult = Regex("""\d+""").findAll(line)
            numbersRegexResult.forEach { number ->
                val symbolCoord = symbolCoordBorderingRange(index, number.range)
                if (symbolCoord != null) {
                    val symbol = engine[symbolCoord.x][symbolCoord.y]
                    if (onlyIncludeGears && symbol != "*") {
                        return@forEach
                    }
                    numbersAndCoordinate.add(Pair(number.value.toInt(), symbolCoord))
                }
            }
        }
        return numbersAndCoordinate
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val engine = Engine(input)

        return engine.getNumbersAndAdjacentSymbolCoordinates().sumOf{ it.first }
    }

    fun part2(input: List<String>): Int {
        val engine = Engine(input)

        val adjacentNumbersAndSymbols = engine.getNumbersAndAdjacentSymbolCoordinates()
        val gearRatios = mutableMapOf<Coordinate, MutableList<Int>>()
        adjacentNumbersAndSymbols.forEach { (number, coordinate) ->
            val isGear = engine.getSymbol(coordinate) == "*"
            if (isGear) {
                gearRatios.getOrPut(coordinate) { mutableListOf() }.add(number)
            }
        }

        return gearRatios.values.filter { it.size == 2 }.sumOf { it.product() }
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}