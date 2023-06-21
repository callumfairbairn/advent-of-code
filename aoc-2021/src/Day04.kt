private val testInput = readInput("Day04_test")
private val realInput = readInput("Day04")

private fun splitRawRowIntoInts(rawRow: String): MutableList<Int> {
    return mutableListOf(
        rawRow.slice(0..1).trim().toInt(),
        rawRow.slice(3..4).trim().toInt(),
        rawRow.slice(6..7).trim().toInt(),
        rawRow.slice(9..10).trim().toInt(),
        rawRow.slice(12..13).trim().toInt(),
    )
}

private fun splitRawInputIntoBoards(rawInput: List<String>): List<Board> {
    val allGroups = mutableListOf<MutableList<String>>()
    var currentGroup = mutableListOf<String>()
    for (row in rawInput.drop(2)) {
        if (row == "") {
            allGroups.add(currentGroup)
            currentGroup = mutableListOf()
        } else {
            currentGroup.add(row)
        }
    }
    allGroups.add(currentGroup)
    return allGroups.map { Board(it) }
}

private fun boardStateMap(boardState: List<List<Int>>): Map<Int, Coordinate> {
    val map = mutableMapOf<Int, Coordinate>()
    for (x in boardState.indices) {
        for (y in boardState.indices) {
            map[boardState[x][y]] = Coordinate(x, y)
        }
    }
    return map
}

private class Board(rawRows: List<String>) {
    private val boardState = rawRows.map { splitRawRowIntoInts(it) }
    private val boardStateMap = boardStateMap(boardState)
    var bingoed = false

    fun bingo(): Boolean {
        val transposedBoardState = transpose(boardState)
        for (state in listOf(boardState, transposedBoardState)) {
            for (row in state) {
                if (row.all { it == -1 }) { return true }
            }
        }
        return false
    }

    fun process(number: Int) {
        val coord = boardStateMap[number]
        if (coord != null) {
            boardState[coord.x][coord.y] = -1
        }
    }

    fun unmarkedNumbersSum(): Int {
        return boardState.flatten().filter { it != -1 }.sumOf { it }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = splitRawInputIntoBoards(input)

        for (number in numbers) {
            boards.forEach {
                it.process(number)
                if (it.bingo()) {
                    return number * it.unmarkedNumbersSum()
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = splitRawInputIntoBoards(input)
        var bingos = 0

        for (number in numbers) {
            boards.forEach {
                it.process(number)
                if (!it.bingoed && it.bingo()) {
                    it.bingoed = true
                    bingos += 1
                    if (bingos == boards.size ) {
                        return number * it.unmarkedNumbersSum()
                    }
                }
            }
        }
        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}