private val testInput = readInput("Day05_test")
private val realInput = readInput("Day05")

private fun getStartAndEndCoords(row: String): Pair<Coordinate, Coordinate> {
    val spaceSplit = row.split(" ")
    val startCoord = Coordinate(spaceSplit[0].split(",")[0].toInt(), spaceSplit[0].split(",")[1].toInt())
    val endCoord = Coordinate(spaceSplit[2].split(",")[0].toInt(), spaceSplit[2].split(",")[1].toInt())
    return Pair(startCoord, endCoord)
}

private fun addCoord(
    newCoord: Coordinate,
    uniqueCoordinates: MutableSet<Coordinate>,
    duplicateCoordinates: MutableSet<Coordinate>,
) {
    if (uniqueCoordinates.contains(newCoord)) {
        duplicateCoordinates.add(newCoord)
    } else {
        uniqueCoordinates.add(newCoord)
    }
}

private fun isDiagonal(startCoord: Coordinate, endCoord: Coordinate): Boolean {
    return startCoord.x != endCoord.x && startCoord.y != endCoord.y
}

private fun processDiagonal(
    startCoord: Coordinate,
    endCoord: Coordinate,
    uniqueCoordinates: MutableSet<Coordinate>,
    duplicateCoordinates: MutableSet<Coordinate>,
) {
    val yIterator = (startCoord.y toward endCoord.y).iterator()
    for (x in startCoord.x toward endCoord.x) {
        val newCoord = Coordinate(x, yIterator.nextInt())
        addCoord(newCoord, uniqueCoordinates, duplicateCoordinates)
    }
}

private fun isHorizontal(startCoord: Coordinate, endCoord: Coordinate): Boolean {
    return startCoord.x == endCoord.x
}

private fun processHorizontal(
    startCoord: Coordinate,
    endCoord: Coordinate,
    uniqueCoordinates: MutableSet<Coordinate>,
    duplicateCoordinates: MutableSet<Coordinate>,
) {
    for (y in startCoord.y toward endCoord.y) {
        val newCoord = Coordinate(startCoord.x, y)
        addCoord(newCoord, uniqueCoordinates, duplicateCoordinates)
    }
}

private fun isVertical(startCoord: Coordinate, endCoord: Coordinate): Boolean {
    return startCoord.y == endCoord.y
}

private fun processVertical(
    startCoord: Coordinate,
    endCoord: Coordinate,
    uniqueCoordinates: MutableSet<Coordinate>,
    duplicateCoordinates: MutableSet<Coordinate>,
) {
    for (x in startCoord.x toward endCoord.x) {
        val newCoord = Coordinate(x, startCoord.y)
        addCoord(newCoord, uniqueCoordinates, duplicateCoordinates)
    }

}

fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        val uniqueCoordinates = mutableSetOf<Coordinate>()
        val duplicateCoordinates = mutableSetOf<Coordinate>()

        input.forEach { row ->
            val (startCoord, endCoord) = getStartAndEndCoords(row)

            if (!isDiagonal(startCoord, endCoord) && isHorizontal(startCoord, endCoord)) {
                processHorizontal(startCoord, endCoord, uniqueCoordinates, duplicateCoordinates)
            }

            if (!isDiagonal(startCoord, endCoord) && isVertical(startCoord, endCoord)) {
                processVertical(startCoord, endCoord, uniqueCoordinates, duplicateCoordinates)
            }
        }

        return duplicateCoordinates.size
    }

    fun part2(input: List<String>): Int {
        val uniqueCoordinates = mutableSetOf<Coordinate>()
        val duplicateCoordinates = mutableSetOf<Coordinate>()

        input.forEach { row ->
            val (startCoord, endCoord) = getStartAndEndCoords(row)

            if (isDiagonal(startCoord, endCoord)) {
                processDiagonal(startCoord, endCoord, uniqueCoordinates, duplicateCoordinates)
            }

            if (isHorizontal(startCoord, endCoord)) {
                processHorizontal(startCoord, endCoord, uniqueCoordinates, duplicateCoordinates)
            }

            if (isVertical(startCoord, endCoord)) {
                processVertical(startCoord, endCoord, uniqueCoordinates, duplicateCoordinates)
            }
        }

        return duplicateCoordinates.size
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}