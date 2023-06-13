import java.math.BigInteger

private val testInput = readInput("Day06_test")
private val realInput = readInput("Day06")

// create a map keeping track of how many of each fish there are
fun getNumberOfFishByDays(input: List<String>, days: Int): BigInteger {
    var fishMap = mutableMapOf(
        Pair(8, BigInteger.valueOf(0)),
        Pair(7, BigInteger.valueOf(0)),
        Pair(6, BigInteger.valueOf(0)),
        Pair(5, BigInteger.valueOf(0)),
        Pair(4, BigInteger.valueOf(0)),
        Pair(3, BigInteger.valueOf(0)),
        Pair(2, BigInteger.valueOf(0)),
        Pair(1, BigInteger.valueOf(0)),
        Pair(0, BigInteger.valueOf(0)),
    )

    input[0].split(',').forEach { fishMap[it.toInt()] = fishMap[it.toInt()]!! + BigInteger.valueOf(1) }
    val dayRange = (1..days)

    dayRange.forEach { _ ->
        val newFishMap = fishMap.toMutableMap()
        for ((key, value) in fishMap.entries) {
            if (key == 0) {
                newFishMap[8] = value
                newFishMap[6] = newFishMap[6]!! + value
                newFishMap[0] = newFishMap[0]!! - value
            } else {
                newFishMap[key - 1] = newFishMap[key - 1]!! + value
                newFishMap[key] = newFishMap[key]!! - value
            }
        }
        fishMap = newFishMap
    }
    return fishMap.values.sumOf { it }
}

fun main(args: Array<String>) {
    fun part1(input: List<String>): BigInteger {
        return getNumberOfFishByDays(input, 80)
    }

    fun part2(input: List<String>): BigInteger {
        return getNumberOfFishByDays(input, 256)
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}