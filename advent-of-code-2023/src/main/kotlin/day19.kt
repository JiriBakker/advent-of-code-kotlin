import util.min
import util.splitByDoubleNewLine
import util.sumOfLong
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.map

private data class WorkflowRule(
    val category: Char?,
    val operator: Char?,
    val value: Int?,
    val destination: String
)

fun day19a(input: List<String>): Long {
    val workflows = input.parseWorkflows()
    val ratings = input.parseRatings()

    return ratings.sumOfLong { rating ->
        var rules = workflows["in"]!!

        while (true) {
            val nextKey = rules.firstNotNullOf { rule -> rating.applyRule(rule) }
            if (nextKey == "R") {
                return@sumOfLong 0L
            } else if (nextKey == "A") {
                println("Reached A with rule $rules for rating $rating")
                return@sumOfLong rating.sumOfLong { it.value }
            }
            rules = workflows[nextKey]!!
        }

        throw Exception("Something went wrong")
    }
}

fun day19b(input: List<String>): Long {
    val workflows = input.parseWorkflows()

    fun reducePossibleRatingsRanges(possibleRatingRanges: Map<Char, IntRange>, currentRules: List<WorkflowRule>, curRuleIndex: Int): List<Map<Char, IntRange>> {
        if (curRuleIndex !in currentRules.indices) return emptyList()

        val curRule = currentRules[curRuleIndex]

        if (curRule.category == null) {
            return when (curRule.destination) {
                "A" -> listOf(possibleRatingRanges)
                "R" -> emptyList()
                else -> reducePossibleRatingsRanges(possibleRatingRanges, workflows[curRule.destination]!!, 0)
            }
        }

        val possibleRatingRangesForCategory = possibleRatingRanges[curRule.category]!!
        val (matchedRange, unmatchedRange) = possibleRatingRangesForCategory.determineMatchRanges(curRule.operator!!, curRule.value!!)

        return when (curRule.destination) {
            "A" ->
                listOfNotNull(
                    matchedRange?.let { listOf(possibleRatingRanges.plus(curRule.category to it)) },
                    unmatchedRange?.let { reducePossibleRatingsRanges(possibleRatingRanges.plus(curRule.category to it), currentRules, curRuleIndex + 1) }
                )

            "R" ->
                listOfNotNull(
                    unmatchedRange?.let { reducePossibleRatingsRanges(possibleRatingRanges.plus(curRule.category to it), currentRules, curRuleIndex + 1) },
                    matchedRange?.let { emptyList() }
                )

            else -> listOfNotNull(
                matchedRange?.let { reducePossibleRatingsRanges(possibleRatingRanges.plus(curRule.category to it), workflows[curRule.destination]!!, 0) },
                unmatchedRange?.let { reducePossibleRatingsRanges(possibleRatingRanges.plus(curRule.category to it), currentRules, curRuleIndex + 1) }
            )
        }.flatten()
    }


    val possibleRatingRanges = reducePossibleRatingsRanges(mapOf('x' to 1 .. 4000, 'm' to 1 .. 4000, 'a' to 1 .. 4000, 's' to 1 .. 4000), workflows["in"]!!, 0)

    return possibleRatingRanges.sumOfLong { possibleRatings ->
        possibleRatings.values.fold(1L) { product, range -> range.count().toLong() * product }
    }
}

private fun IntRange.getOverlapWith(other: IntRange): IntRange? {
    if (this.first in other) return this.first .. min(this.last, other.last)
    if (this.last in other) return other.first .. this.last
    if (this.first < other.first && this.last > other.last) return other
    return null
}

private fun IntRange.determineMatchRanges(operator: Char, value: Int): Pair<IntRange?, IntRange?> {
    if (operator == '>') {
        val matchedRange = this.getOverlapWith(value + 1 .. 4000)
        val unmatchedRange = this.getOverlapWith(1 .. value)
        return Pair(matchedRange, unmatchedRange)
    } else {
        val matchedRange = this.getOverlapWith(1 until value)
        val unmatchedRange = this.getOverlapWith(value .. 4000)
        return Pair(matchedRange, unmatchedRange)
    }
}


private fun List<String>.parseWorkflows() =
    this.splitByDoubleNewLine().first()
        .associate { line ->
            val key = line.split("{").first()

            val rules: List<WorkflowRule> = line.split("{")[1].dropLast(1).split(",").map { rule ->
                if (':' !in rule) {
                    WorkflowRule(null, null, null, rule)
                } else {
                    val category = rule[0]
                    val operator = rule[1]
                    val value = rule.drop(2).split(":")[0].toInt()
                    val destination = rule.drop(2).split(":")[1]

                    WorkflowRule(category, operator, value, destination)
                }
            }

            key to rules
        }

private fun List<String>.parseRatings() =
    this.splitByDoubleNewLine()[1]
        .map { line ->
            val (x, m, a, s) = line.drop(1).dropLast(1).split(",").map { it.split("=")[1].toLong() }
            mapOf('x' to x, 'm' to m, 'a' to a, 's' to s)
        }

private fun Map<Char, Long>.applyRule(workflowRule: WorkflowRule) =
    if (workflowRule.value == null) {
        workflowRule.destination
    } else if (workflowRule.operator == '>') {
        if (this[workflowRule.category]!! > workflowRule.value) workflowRule.destination
        else null
    } else {
        if (this[workflowRule.category]!! < workflowRule.value) workflowRule.destination
        else null
    }
