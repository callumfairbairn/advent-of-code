// https://adventofcode.com/2021/day/14

private val testInput = readInput("Day14_test")
private val realInput = readInput("Day14")

private fun getTemplateAndRules(input: List<String>): Pair<List<String>, MutableMap<String, String>> {
    val template = listOf(input[0])
    val rules = mutableMapOf<String, String>()
    input.drop(2).forEach {
        val comboAndAdditive = it.split(" -> ")
        val combo = comboAndAdditive[0].split("").filter { stuff -> stuff.isNotEmpty() }
        val additive = comboAndAdditive[1]
        rules[combo.joinToString("")] = "${combo[0]}$additive${combo[1]}"
    }
    return Pair(template, rules)
}

private fun getHistogram(template: List<String>): Map<Char, Int> {
    val histogram = mutableMapOf<Char, Int>()
    template.forEachIndexed { index, substring ->
        val thisSubstring = if (index == template.size - 1) substring else substring.dropLast(1)
        for (char in thisSubstring.toCharArray().toSet()) {
            val count = thisSubstring.count { it == char }
            if (histogram[char] == null) {
                histogram[char] = count
            } else {
                histogram[char] = histogram[char]!! + count
            }
        }
    }
    return histogram
}

private fun getTemplateAfterSteps(input: List<String>, steps: Int): List<String> {
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
        if (newTemplate.length > 500) {
            val newTemplate1 = newTemplate.substring(0, newTemplate.length / 2 + 1)
            val newTemplate2 = newTemplate.substring(newTemplate.length / 2)
            return listOf(newTemplate1, newTemplate2)
        }
        return listOf(newTemplate)
    }

    for (step in 0 until steps) {
        "step: $step, template length: ${template.sumOf { it.length }}".println()
        val newTemplate = template.map { getNewValueForSubstring(it) }.toList().flatten()
        template = newTemplate
    }
    "Finished...".println()
    return template
}

fun main() {
    fun part1(input: List<String>): Int {
        val template = getTemplateAfterSteps(input, 10)
        val histogram = getHistogram(template)
        return histogram.values.max() - histogram.values.min()
    }

    fun part2(input: List<String>): Int {
        val template = getTemplateAfterSteps(input, 40)
        val histogram = getHistogram(template)
        return histogram.values.max() - histogram.values.min()
    }

    println(part1(testInput))
    println(part1(realInput))
//    println(part2(testInput))
//    println(part2(realInput))
}