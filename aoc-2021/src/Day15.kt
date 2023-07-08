import kotlin.math.abs

private val testInput = readInput("Day15_test")
private val realInput = readInput("Day15")


fun main() {
    fun part1(input: List<String>): Int {
        val cave = input.map { row -> row.map { it.toString().toInt() } }
        val endLocation = Coordinate(cave[0].size - 1, cave.size - 1)
        val startingRisk = cave[0][0]

        fun getPossibleMoves(coord: Coordinate, visited: Set<Coordinate>): List<Coordinate> {
            val (x, y) = coord
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
            }
        }

        fun getShortestPath(): Map<Coordinate, Int> {
            val visited = mutableSetOf(endLocation)
            var possibleMoves = getPossibleMoves(endLocation, visited).toSet()
            val shortestPathMap = mutableMapOf(endLocation to cave[endLocation.x][endLocation.y])
            var reachedEnd = false

            while(!reachedEnd) {
                possibleMoves.forEach { newMove ->
                    val currentRiskLevel = cave[newMove.x][newMove.y]
                    val nextMoves = getPossibleMoves(newMove, setOf(newMove)).filter { shortestPathMap.containsKey(it) }
                    shortestPathMap[newMove] = currentRiskLevel + nextMoves.minOf { shortestPathMap[it]!! }
                }
                if (possibleMoves.contains(Coordinate(0, 0))) {
                    reachedEnd = true
                } else {
                    visited.addAll(possibleMoves)
                    possibleMoves = possibleMoves.map { getPossibleMoves(it, visited) }.flatten().toSet()
                }
            }


            return shortestPathMap
        }
        fun printShortestPathMap(shortestPathMap: Map<Coordinate, Int>) {
            val maxX = shortestPathMap.keys.maxOf { it.x }
            val maxY = shortestPathMap.keys.maxOf { it.y }
            for (x in 0..maxY) {
                for (y in 0..maxX) {
                    val coord = Coordinate(x, y)
                    val value = shortestPathMap[coord]
                    if (value != null) {
                        print("${if (value.toString().length == 1) "$value " else value} ")
                    } else {
                        print("X ")
                    }
                }
                println()
            }
        }

        val shortestPathMap = getShortestPath()
        printShortestPathMap(shortestPathMap)
        return shortestPathMap[Coordinate(0, 0)]!! - startingRisk
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput))
//    println(part1(realInput))
//    println(part2(testInput))
//    println(part2(realInput))
}