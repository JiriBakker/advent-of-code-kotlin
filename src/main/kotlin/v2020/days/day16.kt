package v2020.days.day16

import util.product

private typealias Ticket = List<Int>

private class FieldRange(private val min1: Int, private val max1: Int, private val min2: Int, private val max2: Int) {
    fun isInRange(value: Int): Boolean =
        (value in min1..max1) || (value in min2..max2)
}

private fun parseInput(input: List<String>): Triple<Map<String, FieldRange>, Ticket, List<Ticket>> {
    val fields = mutableMapOf<String, FieldRange>()
    var index = 0
    while (input[index].isNotEmpty()) {
        val (name, ranges) = input[index].split(": ")
        val fieldRange = ranges
            .split(" or ")
            .map { it.split("-").map(String::toInt) }
            .let { FieldRange(it[0][0], it[0][1], it[1][0], it[1][1]) }
        fields[name] = fieldRange
        index++
    }

    index += 2

    val myTicket = input[index].split(",").map(String::toInt)

    index += 3

    val otherTickets = input.drop(index).map { line ->
        line.split(",").map(String::toInt)
    }

    return Triple(fields, myTicket, otherTickets)
}

fun day16a(input: List<String>): Int {
    val (fields, _, otherTickets) = parseInput(input)

    return otherTickets.flatMap { ticket ->
        ticket.filter { value ->
            fields.none { it.value.isInRange(value) }
        }
    }.sum()
}

private fun <T> List<List<T>>.transpose(): List<List<T>> =
    this.first().indices.map { index -> this.map { it[index] } }

fun day16b(input: List<String>): Long {
    val (fields, myTicket, otherTickets) = parseInput(input)

    val validTickets = otherTickets.filter { ticket ->
        ticket.all { value ->
            fields.any { it.value.isInRange(value) }
        }
    }

    val valuesPerColumn = validTickets.transpose()

    val columnMatches = valuesPerColumn.map { valuesForColumn ->
        fields.filter { field -> valuesForColumn.all { value -> field.value.isInRange(value) } }.map { it.key }.toMutableList()
    }

    val translatedFields = mutableMapOf<String, Int>()
    while (translatedFields.size < myTicket.size) {
        columnMatches.forEachIndexed find@{ index, possibleFields ->
            if (possibleFields.size == 1) {
                val field = possibleFields.first()
                translatedFields[field] = index
                columnMatches.forEach { it.remove(field) }
                return@find
            }
        }
    }

    return translatedFields
        .filterKeys { it.startsWith("departure") }
        .values
        .map { myTicket[it].toLong() }
        .product()
}

