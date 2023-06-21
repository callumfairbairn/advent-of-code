private val testInput = readInput("Day02_test")
private val realInput = readInput("Day02")

class SubmarineStatus {
    var pos = 0
    var depth = 0
    var aim = 0
}

typealias NavSystemMap = Map<String, (value: Int, status: SubmarineStatus) -> Unit>

interface NavSystem {
    val map: NavSystemMap
}

class BasicNavSystem(): NavSystem {
    private fun forward(value: Int, status: SubmarineStatus) { status.pos += value }
    private fun up(value: Int, status: SubmarineStatus) { status.depth -= value }
    private fun down(value: Int, status: SubmarineStatus) { status.depth += value }

    override val map = mapOf("forward" to ::forward, "up" to ::up, "down" to ::down)
}

class AdvancedNavSystem(): NavSystem {
    private fun forward(value: Int, status: SubmarineStatus) {
        status.pos += value
        status.depth += status.aim * value
    }
    private fun up(value: Int, status: SubmarineStatus) { status.aim -= value }
    private fun down(value: Int, status: SubmarineStatus) { status.aim += value }

    override val map = mapOf("forward" to ::forward, "up" to ::up, "down" to ::down)
}

class Submarine(private val navSystemMap: NavSystemMap) {
    private val status = SubmarineStatus()

    fun processCommand(command: String) {
        val direction = command.split(' ')[0]
        val value = command.split(' ')[1].toInt()
        navSystemMap[direction]?.let { it(value, status) }
    }

    fun result(): Int {
        return status.pos * status.depth;
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        val navSystem = BasicNavSystem()
        val submarine = Submarine(navSystem.map)

        input.forEach { submarine.processCommand(it) }

        return submarine.result()
    }

    fun part2(input: List<String>): Int {
        val navSystem = AdvancedNavSystem()
        val submarine = Submarine(navSystem.map)

        input.forEach { submarine.processCommand(it) }

        return submarine.result()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}