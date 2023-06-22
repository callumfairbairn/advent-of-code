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

class CaveSystem(input: List<String>) {
    private val caves = mutableSetOf<Cave>()

    fun getCave(name: String): Cave {
        return caves.find { it.name == name }!!
    }

    fun initiateCaveSystem(input: List<String>) {
        input.forEach { line ->
            line.split("-").forEach { caves.add(Cave(it)) }
        }
        input.forEach { line ->
            val (cave1, cave2) = line.split("-").map { getCave(it) }
            cave1.addConnection(cave2)
            cave2.addConnection(cave1)
        }
    }

    fun getAllPaths(
        start: Cave,
        allPaths: MutableSet<MutableList<Cave>> = mutableSetOf(),
        currentPath: MutableList<Cave> = mutableListOf(start),
    ): MutableSet<MutableList<Cave>> {
        if (start == getCave("end")) {
            allPaths.add(currentPath)
            return allPaths
        }
        val possibleNeighbors = start.connections.filter { it.bigCave || !currentPath.contains(it) }
        val newPaths = possibleNeighbors.map { getAllPaths(it, allPaths, (currentPath + it).toMutableList()) }
        return newPaths.flatten().toMutableSet()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val caveSystem = CaveSystem(input)
        caveSystem.initiateCaveSystem(input)
        val allPaths = caveSystem.getAllPaths(caveSystem.getCave("start"))
        return allPaths.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}