import java.math.BigInteger
import java.util.Stack

private val testInput = readInput("Day10_test")
private val realInput = readInput("Day10")

private val openToCloseMap = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

private val scoreMap1 = mapOf(
    ')' to BigInteger.valueOf(3),
    ']' to BigInteger.valueOf(57),
    '}' to BigInteger.valueOf(1197),
    '>' to BigInteger.valueOf(25137),
)

private val scoreMap2 = mapOf(
    ')' to BigInteger.valueOf(1),
    ']' to BigInteger.valueOf(2),
    '}' to BigInteger.valueOf(3),
    '>' to BigInteger.valueOf(4),
)

fun getAutocompleteScore(remainingCharacters: Stack<Char>): BigInteger {
    val closingStack = Stack<Char>()
    for (char in remainingCharacters) {
        closingStack.add(0, openToCloseMap[char])
    }
    return closingStack.fold(BigInteger.valueOf(0)) { acc, char -> acc * BigInteger.valueOf(5) + scoreMap2[char]!! }
}

fun getScoreAndCorruptedStatus(line: String): Pair<BigInteger, Boolean> {
    val stack = Stack<Char>()
    line.forEach { char ->
        if (char in openToCloseMap.keys) {
            stack.add(char)
        } else {
            val openingParen = stack.pop()
            if (char != openToCloseMap[openingParen]) {
                return Pair(scoreMap1[char]!!, true)
            }
        }
    }
    return Pair(getAutocompleteScore(stack), false)
}

fun main(args: Array<String>) {
    fun part1(input: List<String>): BigInteger {
        return input.map { line -> getScoreAndCorruptedStatus(line) }.filter { (_, isCorrupted) -> isCorrupted }
            .sumOf { (score, _) -> score }
    }

    fun part2(input: List<String>): BigInteger {
        val uncorruptedLines = input.fold(mutableListOf<BigInteger>()) { acc, line ->
            val (score, isCorrupted) = getScoreAndCorruptedStatus(line)
            if (!isCorrupted) {
                acc.add(score)
            }
            acc
        }

        return uncorruptedLines.sortedBy { it }.middle()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}