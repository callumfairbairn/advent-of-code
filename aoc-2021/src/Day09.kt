private val testInput = readInput("Day09_test")
private val realInput = readInput("Day09")

private fun getNeighbors(coord: Coordinate, cave: List<List<Int>>): List<Coordinate> {
    val (x, y) = coord
    val neighbors = mutableListOf<Coordinate>()
    if (x > 0) { neighbors.add(Coordinate(x - 1, y))}
    if (x < cave.lastIndex) { neighbors.add(Coordinate(x + 1, y)) }
    if (y > 0) { neighbors.add(Coordinate(x, y - 1)) }
    if (y < cave[0].lastIndex) { neighbors.add(Coordinate(x, y + 1)) }
    return neighbors
}

private fun getLowPoints(cave: List<List<Int>>): List<Coordinate> {
    val lowPoints = mutableListOf<Coordinate>()

    for (x in cave.indices) {
        for (y in cave[x].indices) {
            val currentValue = cave[x][y]
            if (getNeighbors(Coordinate(x, y), cave).all { neighbor -> currentValue < cave[neighbor.x][neighbor.y] }) {
                lowPoints.add(Coordinate(x, y))
            }
        }
    }
    return lowPoints
}

fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        val cave = input.map { row -> row.map { it.toString().toInt() } }
        val lowPoints = getLowPoints(cave)

        return lowPoints.sumOf { cave[it.x][it.y] + 1 }
    }

    fun part2(input: List<String>): Int {
        val cave = input.map { row -> row.map { it.toString().toInt() } }
        val lowPoints = getLowPoints(cave)
        val basins = mutableListOf<List<Coordinate>>()

        for (lowPoint in lowPoints) {
            val basin = mutableListOf(lowPoint)
            var neighbors = getNeighbors(lowPoint, cave).map { Pair(lowPoint, it) }.toMutableList()
            val seenPoints = mutableListOf(lowPoint)

            while (neighbors.size > 0) {
                val nextNeighbors = mutableListOf<Pair<Coordinate, Coordinate>>()
                for (neighbor in neighbors) {
                    val (referencePoint, currentPoint) = neighbor
                    val referenceValue = cave[referencePoint.x][referencePoint.y]
                    val currentValue = cave[currentPoint.x][currentPoint.y]
                    if (currentValue != 9 && currentValue > referenceValue && !basin.contains(currentPoint)) {
                        basin.add(currentPoint)
                        val newNeighbors = getNeighbors(currentPoint, cave)
                        newNeighbors.forEach {
                             if (!seenPoints.contains(it)) {
                                 nextNeighbors.add((Pair(currentPoint, it)))
                             }
                        }
                    }
                }
                neighbors = nextNeighbors
            }

            basins.add(basin)
        }

        return basins.map { it.size }.sortedByDescending { it }.slice(0..2).product()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}