package util

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.jvmErasure
import kotlin.system.measureTimeMillis

annotation class DoNotAutoExecute

fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println("(took $millis ms)\n")
}

fun autoExecuteSolutions(solution: KFunction<*>, year: Int) {
    if (solution.hasAnnotation<DoNotAutoExecute>()) return

    val filename = solution.name.split("_").first().dropLast(1)
    val input = readInputLines(filename, year)
    val parameters =
        listOfNotNull(
            solution.parameters.firstOrNull()
                ?.let { param ->
                    when (param.type.jvmErasure) {
                        String::class -> param to input.firstOrNull()
                        List::class as KClass<List<String>> -> param to input
                        else -> null
                    }
                }
        ).toMap()

    println(solution.name)
    time {
        println(solution.callBy(parameters).toString())
    }
}