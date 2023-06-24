private val testInput = readInput("Day13_test")
private val realInput = readInput("Day13")

enum class Axis { X, Y }
data class Fold(val axis: Axis, val position: Int)

fun getCoordinatesAndFolds(input: List<String>): Pair<Set<Coordinate>, List<Fold>> {
    val coordinates = mutableSetOf<Coordinate>()
    val folds = mutableListOf<Fold>()
    for (line in input) {
        if (line.startsWith("fold")) {
            val split = line.split("=")
            val axis = if (split[0].contains("x")) Axis.X else Axis.Y
            val position = split[1].trim().toInt()
            folds.add(Fold(axis, position))
        } else if (line != "") {
            val split = line.split(",")
            coordinates.add(Coordinate(split[0].trim().toInt(), split[1].trim().toInt()))
        }
    }
    return Pair(coordinates, folds)
}

fun doFolds(originalCoordinates: Set<Coordinate>, folds: List<Fold>): Set<Coordinate> {
    var coordinates = originalCoordinates.toMutableSet()
    for (fold in folds) {
        if (fold.axis == Axis.X) {
            coordinates = coordinates.map { coordinate ->
                if (coordinate.x > fold.position) {
                    return@map Coordinate(fold.position - (coordinate.x - fold.position), coordinate.y)
                }
                return@map coordinate
            }.toMutableSet()
        } else {
            coordinates = coordinates.map { coordinate ->
                if (coordinate.y > fold.position) {
                    return@map Coordinate(coordinate.x, fold.position - (coordinate.y - fold.position))
                }
                return@map coordinate
            }.toMutableSet()
        }
    }
    return coordinates
}

fun printCoordinates(coordinates: Set<Coordinate>) {
    val minX = coordinates.minOf { it.x }
    val maxX = coordinates.maxOf { it.x }
    val minY = coordinates.minOf { it.y }
    val maxY = coordinates.maxOf { it.y }
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            if (coordinates.contains(Coordinate(x, y))) {
                print("##")
            } else {
                print("..")
            }
        }
        println()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val (coordinates, folds) = getCoordinatesAndFolds(input)
        val coordinatesAfterFolds = doFolds(coordinates, folds.subList(0, 1))

        return coordinatesAfterFolds.size
    }

    fun part2(input: List<String>): Int {
        val (coordinates, folds) = getCoordinatesAndFolds(input)
        val coordinatesAfterFolds = doFolds(coordinates, folds)
        printCoordinates(coordinatesAfterFolds)

        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}