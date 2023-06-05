private val testInput = readInput("Day07_test")
private val realInput = readInput("Day07")

class Map: HashMap<String, Int>() {
    fun add(path: String, value: String) {
        val currentDirSize = this[path]
        if (currentDirSize == null) {
            this[path] = value.toInt()
        } else {
            this[path] = currentDirSize + value.toInt()
        }
    }
}

class Path {
    private var currentPath = ""

    fun changeDirectory(destination: String) {
        if (destination == "..") {
            currentPath = getUpperDirectory(currentPath)
            return
        }
        currentPath = if (currentPath.lastOrNull() == '/' || destination == "/") {
            "$currentPath${destination}"
        } else {
            "$currentPath/${destination}"
        }
    }

    fun getValue(): String {
        return currentPath
    }
}

fun getUpperDirectory(path: String): String {
    if (path == "/") return ""
    val newPath = path.split("/").dropLast(1).joinToString("/")
    return if (newPath == "") "/" else newPath
}

fun getDirectoryTree(input: List<String>): Map {
    val map = Map()
    val path = Path()

    for (line in input) {
        val commands = line.split(" ")

        if (commands[0] == "$") {
            if (commands[1] == "cd") {
                path.changeDirectory(commands[2])
            }
        } else if (commands[0].toIntOrNull() != null) {
            val size = commands[0]
            var currentPath = path.getValue()
            while (currentPath != "") {
                map.add(currentPath, size)
                currentPath = getUpperDirectory(currentPath)
            }
        }
    }
    return map
}

fun part1(input: List<String>): Int {
    val map = getDirectoryTree(input)
    return map.values.filter { it < 100000 }.sum()
}

fun part2(input: List<String>): Int {
    val map = getDirectoryTree(input)
    val unusedSpace = 70000000 - map.getValue("/")
    val spaceNeeded = 30000000 - unusedSpace

    return map.values.filter { it >= spaceNeeded }.min()
}

fun main(args: Array<String>) {
    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}