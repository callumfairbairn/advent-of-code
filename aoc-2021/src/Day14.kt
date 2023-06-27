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
    val substringMap = mutableMapOf<String, String>()

    fun getNewValueForSubstring(substring: String): String {
        // TODO: Store chunks of strings rather than one string

        if (substring.length == 1) {
            throw Exception("Substring cannot be of length 1")
        }
        if (substring in substringMap) {
            return substringMap[substring]!!
        }
        if (substring.length < 8) {
            var newTemplate = ""
            for (j in 0 until substring.length - 1) {
                val (char1, char2) = Pair(substring[j].toString(), substring[j + 1].toString())
                val key = char1 + char2
                newTemplate += "$char1${rules[key]}"
            }
            val result = newTemplate + substring.last()
            substringMap[substring] = result
            return result
        }

        if (substringMap.containsKey(substring)) {
            return substringMap[substring]!!
        }
        val middleIndex = substring.length / 2
        val (part1, part2, part3) = Triple(
            substring.slice(0 until middleIndex),
            substring.slice(middleIndex - 1 .. middleIndex),
            substring.slice(middleIndex until substring.length)
        )
        val part1Result = getNewValueForSubstring(part1)
        val part2Result = getNewValueForSubstring(part2)
        val part3Result = getNewValueForSubstring(part3)
        substringMap[part1] = part1Result
        substringMap[part3] = part3Result
        // TODO: store result as a list (of chunks) rather than a string to avoid heap overflow
        val overallResult = "${part1Result.dropLast(1)}${part2Result.dropLast(1)}$part3Result"
        substringMap[substring] = overallResult
        return overallResult
    }

    for (step in 0 until steps) {
        template = getNewValueForSubstring(template)
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
//    println(part2(realInput))
}