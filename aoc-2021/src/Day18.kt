import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

private val testInput = readInput("Day18_test")
private val realInput = readInput("Day18")

class SnailfishAdder(val numbers: List<String>) {
    fun addAll(): SFnode {
        val queue = LinkedList(numbers)
        var node = SFnode(queue.pop())
        while (queue.isNotEmpty()) {
            node = node.add(queue.pop())
            NodeReducer(node).reduce()
        }
        return node
    }
}

class NodeReducer(private val root: SFnode) {
    // don't need state as root is modified in place
    var state = root

    fun findNodeToExplode(node: SFnode, depth: Int): SFnode? {
        if (node.left == null) {
            return null
        }
        if (depth > 3) {
            return node
        }
        val leftNodeToExplode = findNodeToExplode(node.left!!, depth + 1)
        if (leftNodeToExplode != null) {
            return leftNodeToExplode
        }
        return findNodeToExplode(node.right!!, depth + 1)
    }

    fun findNodeToSplit(node: SFnode?): SFnode? {
        if (node == null) {
            return null
        }
        if (node.value != null && node.value!! >= 10) {
            return node
        }
        val leftNodeToSplit = findNodeToSplit(node.left)
        if (leftNodeToSplit != null) {
            return leftNodeToSplit
        }
        return findNodeToSplit(node.right)
    }

    fun step(): SFnode {
        val nodeToExplode = findNodeToExplode(state, 0)
        if (nodeToExplode != null) {
            nodeToExplode.explode()
            return state
        }
        val nodeToSplit = findNodeToSplit(state)
        if (nodeToSplit != null) {
            nodeToSplit.split()
            return state
        }
        return state
    }

    fun reduce(): SFnode {
        var stable = false
        while (!stable) {
            val prevState = state.toString()
            step()
            if (state.toString() == prevState) {
                stable = true
            }
        }
        return state
    }
}

// SFnode is short for Snailfish node
class SFnode() {
    var value: Int? = null
    var left: SFnode? = null
    var right: SFnode? = null
    var parent: SFnode? = null

    constructor(input: String): this() {
        val trimmed = input.trim()
        if (trimmed.startsWith("[")) {
            val stripped = trimmed.substring(1, trimmed.length - 1)
            val (left, right) = extractLeftAndRight(stripped)
            this.left = SFnode(left)
            this.left!!.setParent(this)
            this.right = SFnode(right)
            this.right!!.setParent(this)
        } else {
            this.value = trimmed.toInt()
        }
    }

    private fun setParent(parent: SFnode): SFnode {
        this.parent = parent
        return this
    }

    override fun toString(): String {
        if (value != null) {
            return value.toString()
        }
        return "[${left.toString()},${right.toString()}]"
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
            right = string.substring(index + 1).trim()
        } else {
            left = string.substringBefore(",").trim()
            right = string.substringAfter(",").trim()
        }
        return Pair(left, right)
    }

    fun add(value: String): SFnode {
        if (value.startsWith("[")) {
            val newParent = SFnode("[${this},${value}]")
            this.parent = newParent
            return newParent
        }
        this.value = this.value?.plus(value.toInt())
        return this
    }

    private fun getRightmost(): SFnode {
        if (right == null) {
            return this
        }
        return right!!.getRightmost()
    }

    private fun getLeftmost(): SFnode {
        if (left == null) {
            return this
        }
        return left!!.getLeftmost()
    }

    fun getClosestLeft(): SFnode? {
        if (parent == null) {
            return null
        }
        if (parent!!.left == this) {
            return parent!!.getClosestLeft()
        }
        return parent!!.left!!.getRightmost()
    }

    fun getClosestRight(): SFnode? {
        if (parent == null) {
            return null
        }
        if (parent!!.right == this) {
            return parent!!.getClosestRight()
        }
        return parent!!.right!!.getLeftmost()
    }

    fun split(): SFnode {
        if (value == null) {
            throw Exception("Cannot split a branch. Only leaves (which must have a value) can be split.")
        }
        val half = value!! / 2.0
        val left = floor(half).toInt()
        val right = ceil(half).toInt()
        this.left = SFnode(left.toString())
        this.left!!.setParent(this)
        this.right = SFnode(right.toString())
        this.right!!.setParent(this)
        this.value = null
        return this
    }

    fun explode() {
        this.left!!.getClosestLeft()?.add(this.left.toString())
        this.right!!.getClosestRight()?.add(this.right.toString())
        this.left = null
        this.right = null
        this.value = 0
    }

    fun createReducer(): NodeReducer {
        return NodeReducer(this)
    }

    fun getMagnitude(): Int {
        val leftValue = if (this.left?.value != null) {
            this.left!!.value!!
        } else {
            this.left!!.getMagnitude()
        }
        val rightValue = if (this.right?.value != null) {
            this.right!!.value!!
        } else {
            this.right!!.getMagnitude()
        }
        return leftValue * 3 + rightValue * 2
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val adder = SnailfishAdder(input)
        val result = adder.addAll()
        result.println()
        return result.getMagnitude()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}