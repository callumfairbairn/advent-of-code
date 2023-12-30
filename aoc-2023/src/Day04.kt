import kotlin.math.pow

private val testInput = readInput("Day04_test")
private val realInput = readInput("Day04")

fun getScratchcardScore(matches: Int): Int {
    return if (matches == 0) 0 else (2.0).pow((matches - 1).toDouble()).toInt()
}

class Scratchcard(winningNumbers: List<Int>, numbersOnCard: List<Int>, val number: Int) {
    val matches = winningNumbers.intersect(numbersOnCard.toSet()).toList().count()
    val score = getScratchcardScore(matches)
}

class ParseScratchcards(input: List<String>) {
    val scratchcards = mutableListOf<Scratchcard>()

    init {
        for (line in input) {
            val (winningNumbersString, numbersOnCardString) = line.split("|")
            val cardNumber = Regex("""\d+""").find(line)!!.value.toInt()
            val winningNumbers = Regex("""\d+""").findAll(winningNumbersString).toList().drop(1).map { it.value.toInt() }
            val numbersOnCard = Regex("""\d+""").findAll(numbersOnCardString).toList().map { it.value.toInt() }
            scratchcards.add(Scratchcard(winningNumbers, numbersOnCard, cardNumber))
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val scratchcards = ParseScratchcards(input).scratchcards
        return scratchcards.sumOf { it.score }
    }

    fun part2(input: List<String>): Int {
        val scratchcards = ParseScratchcards(input).scratchcards
        val scratchcardsCounts = scratchcards.groupingBy { it.number }.eachCount().toMutableMap()
        for (cardNumber in 1 .. scratchcards.size + 1) {
            if (cardNumber in scratchcardsCounts) {
                val count = scratchcardsCounts[cardNumber]!!
                val matches = scratchcards[cardNumber - 1].matches
                for (i in 1..matches) {
                    scratchcardsCounts[i + cardNumber] = (scratchcardsCounts[i + cardNumber] ?: 0) + count
                }
            }
        }

        return scratchcardsCounts.values.sum()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}