import java.math.BigInteger

private val testInput = readInput("Day05_test")
private val realInput = readInput("Day05")

fun transformRange(
    sourceRange: ClosedRange<BigInteger>,
    transformationRange: ClosedRange<BigInteger>,
    transformationFn: (BigInteger) -> BigInteger,
): List<ClosedRange<BigInteger>> {
    val sliceResult = sourceRange.sliceByRange(transformationRange)
    if (sliceResult.size == 1) {
        return listOf(sourceRange)
    }
    val (first, overlappingRange, second) = sourceRange.sliceByRange(transformationRange)
    val transformedOverlappingRange =
        if (overlappingRange != null) transformationFn(overlappingRange.start)..transformationFn(overlappingRange.endInclusive) else null
    return listOfNotNull(first, transformedOverlappingRange, second)
}

class SourceToDestinationMap(private val input: List<String>) {
    private val rangesToDestinationMap = mutableMapOf<ClosedRange<BigInteger>, (BigInteger) -> BigInteger>()

    init {
        for (instruction in input) {
            val (destination, source, rangeLength) = bigIntRegex(instruction)
            val range = source..source + rangeLength - 1.toBigInteger()
            rangesToDestinationMap[range] = { input -> input + destination - source }
        }
    }

    fun get(key: BigInteger): BigInteger {
        for ((range, destinationFunction) in rangesToDestinationMap) {
            if (key in range) {
                return destinationFunction(key)
            }
        }
        return key
    }

    fun get(sourceRanges: List<ClosedRange<BigInteger>>): List<ClosedRange<BigInteger>> {
        return sourceRanges.flatMap { sourceRange ->
            val (transformationRange, transformationFn) = rangesToDestinationMap.entries.first()
            transformRange(sourceRange, transformationRange, transformationFn)
        }
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
            var counter = start
            while (counter < start + range) {
                seeds.add(counter)
                counter++
            }
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
    fun processSeed(seed: BigInteger, sourceMaps: List<SourceToDestinationMap>): BigInteger {
        var currentSeed = seed
        for (sourceMap in sourceMaps) {
            currentSeed = sourceMap.get(currentSeed)
        }
        return currentSeed
    }

    fun part1(input: List<String>): BigInteger {
        val parser = Parser(input)
        val seeds = parser.getSeeds(false)
        val locations = seeds.map { processSeed(it, parser.sourceMaps) }
        return locations.min()
    }

    fun part2(input: List<String>): BigInteger {
        val parser = Parser(input)
        val seeds = parser.getSeeds(true)
        val locations = seeds.map { processSeed(it, parser.sourceMaps) }
        return locations.min()
    }

    println(part1(testInput) == 35.toBigInteger())
    println(part1(realInput) == 174137457.toBigInteger())
    println(part2(testInput) == 46.toBigInteger())
    println(part2(realInput))
}