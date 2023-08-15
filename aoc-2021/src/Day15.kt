private val testInput = readInput("Day15_test")
private val realInput = readInput("Day15")

private fun addWhitespace(string: String, x: Int): String {
    var newString = string
    for (thing in 0 until x) {
        newString += " "
    }
    return newString
}

private data class Node(val coord: Coordinate, val riskOnDiscovery: Int, val pathOnDiscovery: List<Coordinate>) {
    override fun equals(other: Any?): Boolean {
        if (other !is Node) {
            return false
        }
        return other.coord == coord
    }

    override fun hashCode(): Int {
        return coord.hashCode()
    }
}

fun main() {
    fun part1(input: List<String>, maxIntSize: Int): Int {
        val cave = input.map { row -> row.map { it.toString().toInt() } }
        val endLocation = Coordinate(cave[0].size - 1, cave.size - 1)
        val startingRisk = cave[0][0]

        fun getPossibleMoves(node: Node, visited: Set<Coordinate>): List<Node> {
            val (x, y) = node.coord
            val neighbourCoords = setOf(
                Coordinate(x, y - 1),
                Coordinate(x, y + 1),
                Coordinate(x - 1, y),
                Coordinate(x + 1, y),
            )
            return neighbourCoords.filter {
                it.x in cave.indices &&
                        it.y in cave[0].indices &&
                        !visited.contains(it)
            }.map { Node(it, cave[it.x][it.y] + node.riskOnDiscovery, node.pathOnDiscovery + it) }
        }

        fun getShortestPathMap(): Map<Coordinate, Node> {
            val initialNode = Node(endLocation, startingRisk, listOf(endLocation))
            val shortestPathMap = mutableMapOf(endLocation to initialNode)
            var possibleMoves = ArrayDeque(getPossibleMoves(initialNode, shortestPathMap.keys).sortedBy { it.riskOnDiscovery })
            var reachedEnd = false

            while (!reachedEnd) {
                val nextMove = possibleMoves.removeFirst()

                if (nextMove.coord == Coordinate(0, 0)) {
                    reachedEnd = true
                }

                shortestPathMap[nextMove.coord] = nextMove

                val nextMoves = getPossibleMoves(nextMove, shortestPathMap.keys)
                possibleMoves.addAll(nextMoves)
                possibleMoves = ArrayDeque(possibleMoves.sortedBy { it.riskOnDiscovery }.toSet())
            }

            return shortestPathMap
        }

        fun printShortestPathMap(shortestPathMap: Map<Coordinate, Node>) {
            val maxX = shortestPathMap.keys.maxOf { it.x }
            val maxY = shortestPathMap.keys.maxOf { it.y }
            for (x in 0..maxY) {
                for (y in 0..maxX) {
                    val coord = Coordinate(x, y)
                    val value = shortestPathMap[coord]
                    if (value != null) {
                        val risk = value.riskOnDiscovery.toString()
                        print(addWhitespace(risk, maxIntSize + 1 - risk.length))
                    } else {
                        print(addWhitespace("X", maxIntSize))
                    }
                }
                println()
            }
        }

        val shortestPathMap = getShortestPathMap()
        printShortestPathMap(shortestPathMap)
        return shortestPathMap[Coordinate(0, 0)]!!.riskOnDiscovery - startingRisk
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput, 2))
    println(part1(realInput, 3))
//    println(part2(testInput))
//    println(part2(realInput))
}