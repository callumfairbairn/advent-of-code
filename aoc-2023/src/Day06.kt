private val testInput = readInput("Day06_test")
private val realInput = readInput("Day06")

class Race(private val time: Int, private val distance: Int) {}

fun main() {
    fun parseInput(input: List<String>): List<Race> {
        val times = input[0].split(":")[1].trim().numberRegex()
        val distances = input[1].split(":")[1].trim().numberRegex()

        val races = mutableListOf<Race>()
        for (i in times.indices) {
            races.add(Race(times[i], distances[i]))
        }
        return races
    }

    fun part1(input: List<String>): Int {
        val races = parseInput(input)
        

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}