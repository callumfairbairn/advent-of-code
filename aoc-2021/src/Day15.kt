import com.github.mm.coloredconsole.colored
import kotlin.math.floor

private val testInput = readInput("Day15_test")
private val realInput = readInput("Day15")


fun main() {
    data class Node(val coord: Coordinate, val riskOnDiscovery: Int, val pathOnDiscovery: List<Coordinate>) {
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

    class ChitinCave(input: List<String>) {
        var cave = input.map { row -> row.map { it.toString().toInt() } }
        val maxX by lazy { cave.size - 1 }
        val maxY by lazy { cave[0].size - 1 }
        val xIndices by lazy { cave.indices }
        val yIndices by lazy { cave[0].indices }

        operator fun get(coord: Coordinate): Int {
            return cave[coord.x][coord.y]
        }

        fun getNextNodes(node: Node, visited: Set<Coordinate>): List<Node> {
            val (x, y) = node.coord
            val neighbourCoords = setOf(
                Coordinate(x, y - 1),
                Coordinate(x, y + 1),
                Coordinate(x - 1, y),
                Coordinate(x + 1, y),
            )
            return neighbourCoords.filter { it.x in xIndices && it.y in yIndices && !visited.contains(it) }
                .map { Node(it, this[it] + node.riskOnDiscovery, node.pathOnDiscovery + it) }
        }

        fun extend() {
            val initialCave = cave.toList()
            val additionalAreas = mutableMapOf(0 to initialCave)
            for (i in 1..100) {
                val additionalArea = additionalAreas[i - 1]!!.map { row -> row.map {
                    val newValue = it + 1
                    if (newValue > 9) 1 else newValue
                } }
                additionalAreas[i] = additionalArea
            }
            for (i in 1 .. 4) {
                cave = cave + additionalAreas[i]!!
            }
            cave = cave.mapIndexed { x, row ->
                val modifier = floor(x.toDouble() / initialCave.size).toInt()
                val rowIndex = x % initialCave.size
                row + additionalAreas[1 + modifier]!![rowIndex] + additionalAreas[2 + modifier]!![rowIndex] + additionalAreas[3 + modifier]!![rowIndex] + additionalAreas[4 + modifier]!![rowIndex]
            }
        }
    }

    class NodeLog(val cave: ChitinCave, val startNode: Node) {
        val log = mutableMapOf(startNode.coord to startNode)
        val visited = log.keys
        fun add(node: Node) {
            log[node.coord] = node
        }

        fun populate() {
            var nodeQueue = ArrayDeque(cave.getNextNodes(startNode, this.visited).sortedBy { it.riskOnDiscovery })
            var reachedEnd = false

            while (!reachedEnd) {
                val currentNode = nodeQueue.removeFirst()

                if (currentNode.coord == Coordinate(0, 0)) {
                    reachedEnd = true
                }

                this.add(currentNode)

                val nextNodes = cave.getNextNodes(currentNode, this.visited)
                nodeQueue.addAll(nextNodes)
                nodeQueue = ArrayDeque(nodeQueue.toSet().sortedBy { it.riskOnDiscovery })
            }
        }

        fun getLowestRisk() = log[Coordinate(0, 0)]!!.riskOnDiscovery - cave[Coordinate(0, 0)]

        fun print() {
            val shortestPath = log[Coordinate(0, 0)]!!.pathOnDiscovery
            for (x in 0..cave.maxX) {
                for (y in 0..cave.maxY) {
                    val coord = Coordinate(x, y)
                    val risk = cave[coord].toString()
                    if (coord in shortestPath) {
                        colored { print("$risk ".blue.bold) }
                    } else {
                        print("$risk ")
                    }
                }
                kotlin.io.println()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val cave = ChitinCave(input)
        val startCoordinate = Coordinate(cave.maxX, cave.maxY)
        val startRisk = cave[startCoordinate]
        val initialNode = Node(startCoordinate, startRisk, listOf(startCoordinate))
        val nodeLog = NodeLog(cave, initialNode)

        nodeLog.populate()
        nodeLog.print()
        return nodeLog.getLowestRisk()
    }

    fun part2(input: List<String>): Int {
        val cave = ChitinCave(input)
        cave.extend()
        println()
        val startCoordinate = Coordinate(cave.maxX, cave.maxY)
        val startRisk = cave[startCoordinate]
        val initialNode = Node(startCoordinate, startRisk, listOf(startCoordinate))
        val nodeLog = NodeLog(cave, initialNode)

        nodeLog.populate()
        nodeLog.print()
        return nodeLog.getLowestRisk()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}