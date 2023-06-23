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
    val allPaths = mutableSetOf<List<Cave>>()

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

    private fun calculateVisitedASmallCaveTwice(path: List<Cave>): Boolean {
        return path
            .filter { it != Cave("start") && !it.bigCave }
            .groupingBy { it }
            .eachCount()
            .values.all { it < 2 }
    }

    private fun filterInelligibleCaves(currentPath: List<Cave>, smallCavesAllowed: Boolean): (Cave) -> Boolean {
        return { it -> it.bigCave || smallCavesAllowed || !currentPath.contains(it) }
    }

    fun calculateAllPaths(
        start: Cave,
        currentPath: List<Cave> = mutableListOf(start),
    ): List<Cave>? {
        if (start == getCave("end")) { return currentPath }
        val possibleNeighbors = start.connections.filter { it.bigCave || !currentPath.contains(it) }
        possibleNeighbors.mapNotNull { calculateAllPaths(it, currentPath + it) }.forEach { allPaths.add(it) }
        return null
    }

    fun calculateAllPaths2(
        start: Cave,
        currentPath: List<Cave> = mutableListOf(start),
    ): List<Cave>? {
        if (start == getCave("end")) { return currentPath }
        val smallCavesAllowed = calculateVisitedASmallCaveTwice(currentPath)
        val possibleNeighbors = start.connections.filter(filterInelligibleCaves(currentPath, smallCavesAllowed))
        possibleNeighbors.mapNotNull { calculateAllPaths2(it, currentPath + it) }.forEach { allPaths.add(it) }
        return null
    }
}
fun main() {
    fun part1(input: List<String>): Int {
        val caveSystem = CaveSystem(input)
        caveSystem.initiateCaveSystem()
        caveSystem.calculateAllPaths(caveSystem.getCave("start"))
        return caveSystem.allPaths.size
    }

    fun part2(input: List<String>): Int {
        val caveSystem = CaveSystem(input)
        caveSystem.initiateCaveSystem()
        caveSystem.calculateAllPaths2(caveSystem.getCave("start"))
        return caveSystem.allPaths.size
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}