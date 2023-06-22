private val testInput = readInput("Day12_test")
private val realInput = readInput("Day12")

class Cave(val name: String) {
    val connections: MutableList<Cave> = mutableListOf()
    val bigCave = name.uppercase() == name

    fun addConnection(cave: Cave) {
        connections.add(cave)
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return (other is Cave) && other.name == name
    }

    override fun toString(): String {
        return name
    }
}

class CaveSystem(val input: List<String>) {
    private val caves = mutableSetOf<Cave>()

    fun getCave(name: String): Cave {
        return caves.find { it.name == name }!!
    }

    fun initiateCaveSystem() {
        input.forEach { line ->
            line.split("-").forEach { caves.add(Cave(it)) }
        }
        input.forEach { line ->
            val (cave1, cave2) = line.split("-").map { getCave(it) }
            if (cave2.name != "start") {
                cave1.addConnection(cave2)
            }
            if (cave1.name != "start") {
                cave2.addConnection(cave1)
            }
        }
    }

    fun getAllPaths(
        start: Cave,
        allPaths: Set<List<Cave>> = mutableSetOf(),
        currentPath: List<Cave> = mutableListOf(start),
    ): Set<List<Cave>> {
        if (start == getCave("end")) {
            return setOf(currentPath)
        }
        val possibleNeighbors = start.connections.filter { it.bigCave || !currentPath.contains(it) }
        val newPaths = possibleNeighbors.map { getAllPaths(it, allPaths, currentPath + it) }
        return allPaths + newPaths.flatten()
    }

    fun getAllPaths2(
        start: Cave,
        allPaths: Set<List<Cave>> = mutableSetOf(),
        currentPath: List<Cave> = mutableListOf(start),
        beenToASmallCaveTwice: Boolean = false,
    ): Set<List<Cave>> {
        if (start == getCave("end")) {
            return setOf(currentPath)
        }
        val hasBeenToASmallCaveTwice = beenToASmallCaveTwice ||
                currentPath
                    .filter { it != Cave("start") && !it.bigCave }
                    .groupingBy { it }
                    .eachCount()
                    .values.any { it > 1 }
        val possibleNeighbors = start.connections.filter {
            it.bigCave || !hasBeenToASmallCaveTwice || !currentPath.contains(it)
        }
        val newPaths = possibleNeighbors.map {
            getAllPaths2(
                it,
                allPaths,
                currentPath + it,
                hasBeenToASmallCaveTwice
            )
        }
        return allPaths + newPaths.flatten()
    }

}

fun main() {
    fun part1(input: List<String>): Int {
        val caveSystem = CaveSystem(input)
        caveSystem.initiateCaveSystem()
        val allPaths = caveSystem.getAllPaths(caveSystem.getCave("start"))
        return allPaths.size
    }

    fun part2(input: List<String>): Int {
        val caveSystem = CaveSystem(input)
        caveSystem.initiateCaveSystem()
        val allPaths = caveSystem.getAllPaths2(caveSystem.getCave("start"))
        return allPaths.size
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}