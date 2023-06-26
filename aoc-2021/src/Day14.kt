// https://adventofcode.com/2021/day/14

private val testInput = readInput("Day14_test")
private val realInput = readInput("Day14")

fun getTemplateAndRules(input: List<String>): Pair<String, Map<String, String>> {
    val template = input[0]
    val rules = mutableMapOf<String, String>()
    input.drop(2).forEach {
        val split = it.split(" -> ")
        rules[split[0]] = split[1]
    }
    return Pair(template, rules)
}

fun getHistogram(template: String): Map<Char, Int> {
    val histogram = mutableMapOf<Char, Int>()
    for (char in template.toCharArray().toSet()) {
        histogram[char] = template.count { it == char }
    }
    return histogram
}

fun getTemplateAfterSteps(input: List<String>, steps: Int): String {
    var (template, rules) = getTemplateAndRules(input)
    for (i in 0 until steps) {
        var newTemplate = ""
        for (j in 0 until template.length - 1) {
            val (char1, char2) = Pair(template[j].toString(), template[j + 1].toString())
            val key = char1 + char2
            newTemplate += "$char1${rules[key]}"
        }
        template = newTemplate + template.last()
    }
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
    println(part2(testInput))
    println(part2(realInput))
}