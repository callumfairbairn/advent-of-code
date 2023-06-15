private val testInput = readInput("Day08_test")
private val realInput = readInput("Day08")

private val segmentsToNumberMap = mapOf(
    "abcefg" to 0,
    "cf" to 1,
    "acdeg" to 2,
    "acdfg" to 3,
    "bcdf" to 4,
    "abdfg" to 5,
    "abdefg" to 6,
    "acf" to 7,
    "abcdefg" to 8,
    "abcdfg" to 9,
)

private fun String.intersect(otherString: String): String {
    return split("").filter { otherString.split("").contains(it) }.joinToString("")
}

private fun String.subtract(otherString: String): String {
    return split("").filter { it != "" }.filterNot { otherString.split("").contains(it) }.joinToString("")
}

private fun String.subtract(char: Char): String {
    return split("").filter { it != "" }.filterNot { it[0] == char }.joinToString("")
}

private fun String.countChars(): Map<Char, Int> {
    val charCountMap = mutableMapOf<Char, Int>()
    forEach { charCountMap[it] = if (charCountMap[it] != null) charCountMap[it]!! + 1 else 1 }
    return charCountMap
}

fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        val uniqueLengths = listOf(2, 3, 4, 7)
        var counter = 0
        for (row in input) {
            val outputValues = row.split("|")[1].split(" ")
            for (value in outputValues) {
                if (uniqueLengths.contains(value.length)) {
                    counter++
                }
            }
        }

        return counter
    }

    fun part2(input: List<String>): Int {
        val allOutputIntegers = mutableListOf<Int>()

        for (row in input) {
            val numberToSegmentsMap = mutableMapOf<Int, String>()
            val segmentAliases = mutableMapOf<Char, Char>()
            val lengthFiveSegments = mutableSetOf<String>()
            val lengthSixSegments = mutableSetOf<String>()

            val inputValues = row.split("|")[0].split(" ").map { it.alphabetized() }.filter { it != "" }
            val outputValues = row.split("|")[1].split(" ").map { it.alphabetized() }.filter { it != "" }

            // set segments for 1, 4, 7, 8
            for (value in inputValues.plus(outputValues)) {
                when (value.length) {
                    2 -> numberToSegmentsMap[1] = value
                    3 -> numberToSegmentsMap[7] = value
                    5 -> lengthFiveSegments.add(value)
                    6 -> lengthSixSegments.add(value)
                    4 -> numberToSegmentsMap[4] = value
                    7 -> numberToSegmentsMap[8] = value
                }
            }

            segmentAliases['a'] = numberToSegmentsMap[7]!!.subtract(numberToSegmentsMap[1]!!)[0]
            val fiveSegmentsOccurences = lengthFiveSegments.joinToString("").countChars()
            val BE = fiveSegmentsOccurences.filter { it.value == 1 }.keys.joinToString("")
            val ADG = fiveSegmentsOccurences.filter { it.value == 3 }.keys.joinToString("")
            segmentAliases['e'] = BE.subtract(numberToSegmentsMap[4]!!)[0]
            segmentAliases['b'] = BE.subtract(segmentAliases['e']!!)[0]
            segmentAliases['g'] = ADG.subtract(numberToSegmentsMap[4]!!).subtract(segmentAliases['a'].toString())[0]
            val five = lengthFiveSegments.filter { it.contains(segmentAliases['b']!!) }[0]
            segmentAliases['f'] = five.intersect(numberToSegmentsMap[1]!!)[0]
            segmentAliases['c'] = numberToSegmentsMap[1]!!.subtract(segmentAliases['f']!!)[0]
            segmentAliases['d'] =
                numberToSegmentsMap[4]!!.subtract("${segmentAliases['b']!!}${segmentAliases['c']!!}${segmentAliases['f']!!}")[0]

            val invertedSegmentAliases = segmentAliases.entries.associateBy({ it.value }, { it.key })

            val outputIntegers = outputValues.map { value ->
                segmentsToNumberMap[value.map { invertedSegmentAliases[it] }.joinToString("").alphabetized()]
            }
            allOutputIntegers.add(outputIntegers.joinToString("").toInt())

        }

        return allOutputIntegers.sum()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}