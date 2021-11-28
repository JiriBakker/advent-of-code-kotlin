package util

import kotlin.reflect.KFunction
import kotlin.reflect.full.hasAnnotation
import kotlin.system.measureTimeMillis

annotation class NotYetImplemented

fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println("(took $millis ms)\n")
}

fun executeSolution(solution: KFunction<*>, year: Int) {
    if (solution.hasAnnotation<NotYetImplemented>()) return

    val filename = solution.name.dropLast(1)
    val input = readInputLines(filename, year)
    try {
        time {
            println(solution.name)
            println(solution.callBy(listOfNotNull(solution.parameters.firstOrNull()?.let { it to input }).toMap()).toString())
        }
    } catch (e: NotImplementedError) {
        println("-- not yet implemented --")
    }
}