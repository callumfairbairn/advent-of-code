private val testInput = readInput("Day03_test")
private val realInput = readInput("Day03")

private fun getGammaAndEpsilon(input: List<String>): Pair<String, String> {
    val cols = MutableList(input[0].length) { 0 }

    input.forEach { line ->
        line.forEachIndexed { col, value ->
            cols[col] += (if (value == '1') 1 else -1)
        }
    }

    val gamma = cols.map { if (it == 0) 1 else if (it > 0) 1 else 0 }.joinToString("")
    val epsilon = cols.map { if (it == 0) 0 else if (it > 0) 0 else 1 }.joinToString("")

    return Pair(gamma, epsilon)
}

fun main() {
    fun part1(input: List<String>): Int {
        val (gamma, epsilon) = getGammaAndEpsilon(input)
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var oxygenRows = input
        var co2Rows = input

        for (i in input[0].indices) {
            val (gamma) = getGammaAndEpsilon(oxygenRows)
            val (_, epsilon) = getGammaAndEpsilon(co2Rows)

            if (oxygenRows.size > 1) { oxygenRows = oxygenRows.filter { row -> row[i] == gamma[i] } }
            if (co2Rows.size > 1) { co2Rows = co2Rows.filter { row -> row[i] == epsilon[i] } }
        }

        return oxygenRows[0].toInt(2) * co2Rows[0].toInt(2)
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}