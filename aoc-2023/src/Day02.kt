private val testInput = readInput("Day02_test")
private val realInput = readInput("Day02")

data class CubeGameSet(var red: Int?, var green: Int?, var blue: Int?)

val cubeType = Regex("""red|green|blue""")

fun cubeConundrumParser(input: List<String>): List<List<CubeGameSet>> {
    return input.map { line ->
        val split = line.split(":")
        split[1].split(";").map { set ->
            val cubeStrings = set.split(",")
            var red: Int? = null
            var green: Int? = null
            var blue: Int? = null
            cubeStrings.forEach { cubeString ->
                val type = cubeType.find(cubeString)?.value
                when (type) {
                    "red" -> red = numberRegex.find(cubeString)?.value?.toInt()
                    "green" -> green = numberRegex.find(cubeString)?.value?.toInt()
                    "blue" -> blue = numberRegex.find(cubeString)?.value?.toInt()
                }
            }
            CubeGameSet(red, green, blue)
        }
    }
}

fun findIDsOfPossibleGames(games: List<List<CubeGameSet>>): List<Int> {
    val limits = CubeGameSet(12, 13, 14)
    val ids = mutableListOf<Int>()
    games.forEachIndexed { index, game ->
        val possible = game.all { cube ->
            if (cube.red != null && cube.red!! > limits.red!!) {
                return@all false
            }
            if (cube.blue != null && cube.blue!! > limits.blue!!) {
                return@all false
            }
            if (cube.green != null && cube.green!! > limits.green!!) {
                return@all false
            }
            true
        }
        if (possible) {
            ids.add(index + 1)
        }
    }
    return ids
}

fun findMinimumSetsOfCumbs(games: List<List<CubeGameSet>>): List<CubeGameSet> {
    return games.map { game ->
        val minSet = CubeGameSet(0, 0, 0)
        game.forEach { cube ->
            if (cube.red != null && cube.red!! > minSet.red!!) {
                minSet.red = cube.red
            }
            if (cube.blue != null && cube.blue!! > minSet.blue!!) {
                minSet.blue = cube.blue
            }
            if (cube.green != null && cube.green!! > minSet.green!!) {
                minSet.green = cube.green
            }
        }
        minSet
    }
}

fun calculatePowerLevel(set: CubeGameSet): Int {
    return set.red!! * set.green!! * set.blue!!
}

fun main() {
    fun part1(input: List<String>): Int {
        val games = cubeConundrumParser(input)
        return findIDsOfPossibleGames(games).sum()
    }

    fun part2(input: List<String>): Int {
        val games = cubeConundrumParser(input)
        val minSets = findMinimumSetsOfCumbs(games)
        return minSets.sumOf { calculatePowerLevel(it) }
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}