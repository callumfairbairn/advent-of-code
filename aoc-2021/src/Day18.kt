import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

private val testInput = readInput("Day18_test")
private val realInput = readInput("Day18")

enum class Side { LEFT, RIGHT }

// SFnode is short for Snailfish node
class SFnode() {
    var value: Int? = null
    var left: SFnode? = null
    var right: SFnode? = null
    var parent: SFnode? = null
    private var side: Side? = null

    constructor(left: SFnode, right: SFnode) : this() {
        left.setParent(this)
        this.left = left
        this.left!!.setSide(Side.LEFT)
        right.setParent(this)
        this.right = right
        this.right!!.setSide(Side.RIGHT)
    }

    constructor(value: Int) : this() {
        this.value = value
    }

    fun setValue(value: Int): SFnode {
        this.value = value
        return this
    }

    fun setLeft(left: SFnode): SFnode {
        this.left = left
        return this
    }

    fun setRight(right: SFnode): SFnode {
        this.right = right
        return this
    }

    private fun setParent(parent: SFnode): SFnode {
        this.parent = parent
        return this
    }

    private fun setSide(side: Side): SFnode {
        this.side = side
        return this
    }

    override fun toString(): String {
        if (value != null) {
            return value.toString()
        }
        return "[${left.toString()}, ${right.toString()}]"
    }

    override fun equals(other: Any?): Boolean {
        return other.toString() == toString()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    private fun extractLeftAndRight(string: String): Pair<String, String> {
        val left: String
        val right: String
        if (string.startsWith("[")) {
            var index = 1
            val stack = Stack<Char>()
            stack.push('[')
            while (stack.isNotEmpty()) {
                val char = string[index]
                if (char == '[') {
                    stack.push('[')
                } else if (char == ']') {
                    stack.pop()
                }
                index++
            }
            left = string.substring(0, index)
            right = string.substring(index + 2).trim()
        } else {
            left = string.substringBefore(",").trim()
            right = string.substringAfter(",").trim()
        }
        return Pair(left, right)
    }

    fun parse(input: String): SFnode {
        val trimmed = input.trim()
        if (trimmed.startsWith("[")) {
            val stripped = trimmed.substring(1, trimmed.length - 1)
            val (left, right) = extractLeftAndRight(stripped)
            return SFnode(parse(left), parse(right))
        }
        return SFnode(trimmed.toInt())
    }

    fun split(): SFnode {
        if (value == null) {
            throw Exception("Cannot split a branch. Only leaves (which must have a value) can be split.")
        }
        val half = value!! / 2.0
        val left = floor(half).toInt()
        val right = ceil(half).toInt()
        return SFnode(SFnode(left), SFnode(right))
    }

    fun getClosestLeft(): SFnode? {
        if (parent == null) {
            return null
        }
        if (parent!!.left == this) {
            return parent!!.getClosestLeft()
        }
        return parent!!.left
    }

//    fun explode(): SFnode {
//        if (parent?.left != null) {
//
//        }
//        return SFnode(0)
//    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}