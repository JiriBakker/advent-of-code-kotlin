package v2021

// Based on MONAD logic
private fun List<String>.toComputeFunctions() =
    (5 .. 239 step 18)
        .map { i ->
            Pair(
                this[i].split(" ")[2].toInt(),
                this[i + 10].split(" ")[2].toInt()
            )
        }
        .map { (restOffset, divOffset) ->
            { w: Int, zRequired: Int ->
                if (restOffset < 0) {
                    val zRest = w - restOffset
                    (zRequired * 26) + zRest
                } else {
                    val zRemain = zRequired - w - divOffset
                    if (zRemain % 26 == 0) {
                        zRemain / 26
                    } else {
                        null
                    }
                }
            }
        }

private typealias ComputeFunc = (Int, Int) -> Int?

private data class ModelNrDigitOption(
    val index: Int,
    val digit: Int?,
    val zRequired: Int,
    private val parent: ModelNrDigitOption?
) {

    fun toModelNr(): Long {
        val modelNr = StringBuilder()
        modelNr.append(digit)

        var curDigit = this.parent
        while (curDigit?.parent != null) {
            modelNr.append(curDigit.digit)
            curDigit = curDigit.parent
        }

        return modelNr.toString().toLong()
    }
}

private fun findModelNr(computeFunctions: List<ComputeFunc>, digitRange: IntProgression): Long {

    fun findZRequiredPerDigit(computeFunc: ComputeFunc, zRequired: Int): Sequence<Pair<Int, Int>> {
        return sequence {
            digitRange.forEach { digit ->
                val zIn = computeFunc(digit, zRequired)
                if (zIn != null) {
                    yield(digit to zIn)
                }
            }
        }
    }

    fun findDigits(prevOption: ModelNrDigitOption): Sequence<ModelNrDigitOption> {
        if (prevOption.index == 0) {
            return sequence {
                if (prevOption.zRequired == 0) {
                    yield(prevOption)
                }
            }
        }

        return findZRequiredPerDigit(computeFunctions[prevOption.index - 1], prevOption.zRequired)
            .flatMap { (digit, nextZRequired) ->
                findDigits(ModelNrDigitOption(prevOption.index - 1, digit, nextZRequired, prevOption))
            }
    }

    val lastModelNrDigit = findDigits(ModelNrDigitOption(14, null, 0, null)).firstOrNull()

    return lastModelNrDigit?.toModelNr()
        ?: throw Error("Unable to find model number")
}

fun day24a(input: List<String>): Long {
    val computeFunctions = input.toComputeFunctions()
    return findModelNr(computeFunctions, (9 downTo 1))
}

fun day24b(input: List<String>): Long {
    val computeFunctions = input.toComputeFunctions()
    return findModelNr(computeFunctions, (1 .. 9))
}
