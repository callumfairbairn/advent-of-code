import java.math.BigInteger

// https://adventofcode.com/2021/day/14

private val testInput = readInput("Day14_test")
private val realInput = readInput("Day14")

private fun getTemplateAndRules(input: List<String>): Pair<MutableMap<String, BigInteger>, MutableMap<String, String>> {
    val template = mutableMapOf(input[0] to BigInteger.ONE)
    val rules = mutableMapOf<String, String>()
    input.drop(2).forEach {
        val comboAndAdditive = it.split(" -> ")
        val combo = comboAndAdditive[0].split("").filter { stuff -> stuff.isNotEmpty() }
        val additive = comboAndAdditive[1]
        rules[combo.joinToString("")] = "${combo[0]}$additive${combo[1]}"
    }
    return Pair(template, rules)
}

private fun getCharacterHistogram(template: Map<String, BigInteger>, lastCharacter: Char): Map<Char, BigInteger> {
    val histogram = mutableMapOf<Char, BigInteger>()
    template.entries.forEachIndexed { index, entry ->
        val (substring, substringCount) = entry
        val thisSubstring = if (index == template.size - 1) substring else substring.dropLast(1)
        for (char in thisSubstring.toCharArray().toSet()) {
            val count = thisSubstring.count { it == char }.toBigInteger()
            if (histogram[char] == null) {
                histogram[char] = count.multiply(substringCount)
            } else {
                histogram[char] = histogram[char]!! + count * substringCount
            }
        }
    }
    histogram[lastCharacter] = histogram[lastCharacter]!! + BigInteger.ONE
    return histogram
}

private fun getTemplateAfterSteps(input: List<String>, steps: Int): Map<String, BigInteger> {
    var (template, rules) = getTemplateAndRules(input)

    fun getNewValueForSubstring(substring: String): List<String> {
        var newTemplate = ""
        var (i, j) = Pair(0, 1)
        if (rules.contains(substring)) {
            newTemplate = rules[substring]!!
        } else {
            while (i < substring.length && j < substring.length) {
                val thisSubstring = substring.substring(i, j + 1)
                if (!rules.contains(thisSubstring)) {
                    val lastSubstringResult = rules[substring.substring(i, j)]!!
                    val newKey = substring.substring(j - 1, j + 1)
                    val thisResult = "${lastSubstringResult.dropLast(1)}${rules[newKey]!!}"
                    rules[thisSubstring] = thisResult
                    newTemplate += if (j == substring.length - 1) {
                        thisResult
                    } else {
                        thisResult.dropLast(1)
                    }
                    i = j
                    j = i + 1
                } else if (j == substring.length - 1) {
                    newTemplate += rules[thisSubstring]!!
                    j++
                }
                else {
                    j++
                }
            }
        }
        if (newTemplate.length > 50) {
            val newTemplate1 = newTemplate.substring(0, newTemplate.length / 2 + 1)
            val newTemplate2 = newTemplate.substring(newTemplate.length / 2)
            return listOf(newTemplate1, newTemplate2)
        }
        return listOf(newTemplate)
    }

    for (step in 0 until steps) {
        "step: $step, template length: ${template.values.sumOf { it }}".println()
        val newTemplate = template.entries.fold(mutableMapOf<String, BigInteger>()) { newTemplate, entry ->
            val (substring, count) = entry
            val newValues = getNewValueForSubstring(substring)
            newValues.forEach {
                if (it in newTemplate) {
                    newTemplate[it] = newTemplate[it]!! + count
                } else {
                    newTemplate[it] = count
                }
            }
            newTemplate
        }
        template = newTemplate
    }
    "Finished...".println()
    return template
}

fun main() {
    fun part1(input: List<String>): BigInteger {
        val template = getTemplateAfterSteps(input, 10)
        val histogram = getCharacterHistogram(template, input[0].last())
        return histogram.values.max() - histogram.values.min()
    }

    fun part2(input: List<String>): BigInteger {
        val template = getTemplateAfterSteps(input, 40)
        val histogram = getCharacterHistogram(template, input[0].last())
        return histogram.values.max() - histogram.values.min()
    }

    println(part1(testInput))
    println(part1(realInput))
    println(part2(testInput))
    println(part2(realInput))
}