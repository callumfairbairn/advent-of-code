import java.math.BigInteger

private val testInput = readInput("Day05_test")
private val realInput = readInput("Day05")

class SourceToDestinationMap(private val input: List<String>) {
    private val rangesToDestinationMap = mutableMapOf<Pair<BigInteger, BigInteger>, (input: BigInteger) -> BigInteger>()

    init {
        for (instruction in input) {
            val (destination, source, rangeLength) = bigIntRegex(instruction)
            val range = Pair(source, source + rangeLength)
            rangesToDestinationMap[range] = { input -> input + destination - source }
        }
    }

    private fun inRange(key: BigInteger, range: Pair<BigInteger, BigInteger>): Boolean {
        return !(key < range.first || key >= range.second)
    }

    fun get(key: BigInteger): BigInteger {
        for ((range, destinationFunction) in rangesToDestinationMap) {
            if (inRange(key, range)) {
                return destinationFunction(key)
            }
        }
        return key
    }
}

class Parser(private val input: List<String>) {
    private fun getInstructions(name: String): List<String> {
        val startIndex = input.indexOfFirst { it.startsWith(name) } + 1
        val range = input.slice(startIndex until input.size).indexOfFirst { it == "" }
        val endIndex = if (range == -1) input.size else range + startIndex
        return input.subList(startIndex, endIndex)
    }

    fun getSeeds(seedRanges: Boolean = false): List<BigInteger> {
        val parsedSeedValues = bigIntRegex(input[0])
        if (!seedRanges) {
            return parsedSeedValues
        }
        val seeds = mutableListOf<BigInteger>()
        for ((start, range) in parsedSeedValues.chunked(2)) {
//            var counter = start
//            while (counter < start + range) {
//                seeds.add(counter)
//                counter++
//            }
        }
        return seeds
    }

    val sourceMaps = listOf(
        SourceToDestinationMap(getInstructions("seed-to-soil")),
        SourceToDestinationMap(getInstructions("soil-to-fertilizer")),
        SourceToDestinationMap(getInstructions("fertilizer-to-water")),
        SourceToDestinationMap(getInstructions("water-to-light")),
        SourceToDestinationMap(getInstructions("light-to-temperature")),
        SourceToDestinationMap(getInstructions("temperature-to-humidity")),
        SourceToDestinationMap(getInstructions("humidity-to-location")),
    )
}

fun main() {
    fun part1(input: List<String>): BigInteger {
        val parser = Parser(input)
        var currentValues = parser.getSeeds(false)
        for (sourceMap in parser.sourceMaps) {
            currentValues.println()
            currentValues = currentValues.map { sourceMap.get(it) }
        }
        currentValues.println()
        return currentValues.min()
    }

    fun part2(input: List<String>): BigInteger {
        val parser = Parser(input)
        var currentValues = parser.getSeeds(true)
        for (sourceMap in parser.sourceMaps) {
            currentValues.println()
            currentValues = currentValues.map { sourceMap.get(it) }
        }
        currentValues.println()
        return currentValues.min()
    }

    println(part1(testInput))
    println(part1(realInput))
//    println(part2(testInput))
//    println(part2(realInput))
}