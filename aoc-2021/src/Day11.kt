import kotlin.collections.ArrayDeque

private val testInput = readInput("Day11_test")
private val realInput = readInput("Day11")

class Octopus(startingEnergy: Int) {
    var energy = startingEnergy
    var flashes = 0
    private val neighbours = mutableListOf<Octopus>()

    fun setNeighbours(neighbours: List<Octopus>) {
        neighbours.forEach { this.neighbours.add(it) }
    }

    fun incrementEnergy(): List<Octopus> {
        energy++
        if (energy == 10) {
            flashes++
            return neighbours
        }
        return mutableListOf()
    }

    fun resetEnergy() {
        if (energy > 9) {
            energy = 0
        }
    }
}

class Grid(val input: List<String>) {
    private fun createOctopuses(): List<List<Octopus>> {
        return input.map { row -> row.map { column -> Octopus(column.digitToInt()) }.toMutableList() }
    }

    val octopuses = createOctopuses()

    private fun getNeighbors(coord: Coordinate): List<Octopus> {
        val (x, y) = coord
        val neighbourCoords = listOf(
            Coordinate(x, y - 1),
            Coordinate(x, y + 1),
            Coordinate(x - 1, y),
            Coordinate(x + 1, y),
            Coordinate(x - 1, y - 1),
            Coordinate(x + 1, y + 1),
            Coordinate(x - 1, y + 1),
            Coordinate(x + 1, y - 1),
        )
        return neighbourCoords.filter { (x, y) -> x in octopuses.indices && y in octopuses[0].indices }.map { (x, y) -> octopuses[x][y] }
    }

    fun setAllNeighbours() {
        octopuses.forEachIndexed { x, row ->
            row.forEachIndexed { y, octopus ->
                octopus.setNeighbours(getNeighbors(Coordinate(x, y)))
            }
        }
    }

    fun step(): Int {
        var flashesThisStep = 0
        val octopusesToIncrement = ArrayDeque(octopuses.flatten())

        while (octopusesToIncrement.isNotEmpty()) {
            val octopus = octopusesToIncrement.removeFirst()
            val neighbours = octopus.incrementEnergy()
            if (neighbours.isNotEmpty()) {
                flashesThisStep++
                neighbours.forEach { octopusesToIncrement.add(it) }
            }
        }

        octopuses.forEach { row -> row.forEach { octopus -> octopus.resetEnergy() } }
        return flashesThisStep
    }

    fun totalFlashes(): Int {
        return octopuses.flatten().sumOf { octopus -> octopus.flashes }
    }

    override fun toString(): String {
        return octopuses.joinToString("\n") { row ->
            row.joinToString(" ") { octopus ->
                val energyString = octopus.energy.toString()
                if (energyString.length == 1) " $energyString" else energyString
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val grid = Grid(input)
        grid.setAllNeighbours()

        for (i in 1..100) {
            grid.step()
        }

        grid.toString().println()
        "-------------------".println()
        return grid.totalFlashes()
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)
        grid.setAllNeighbours()

        var stepCount = 0
        var flashesThisStep = 0

        while (flashesThisStep < grid.octopuses.flatten().size) {
            stepCount++
            flashesThisStep = grid.step()
        }

        grid.toString().println()
        "-------------------".println()
        return stepCount
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}