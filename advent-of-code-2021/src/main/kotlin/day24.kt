/*
    After analysing the input the MONAD behaviour can be rewritten as follows:

    w          = model nr input digit
    zRequired  = z value of next digit
    zIn        = z value to use as input which will result in zRequired as output
    restOffset = variable read from input instructions (each 6th line, 3rd element)
    divisor    = 26 if restOffset < 0, 1 if restOffset > 0
    divOffset  = variable read from input instructionns (each 16th line, 3rd element)

    x1 = (zIn % 26) + restOffset
    x2 = x1 != w ? 1 : 0

    zRequired = ((zIn / divisor) * ((25 * x2) + 1)) + ((w + divOffset) * x2)

    If restOffset < 0 it is possible to have x1 == w, which results in x2 being 0.
    The resulting formula to find zIn can then be deduced as follows (note divisor will be 26):

    w = x1
    w = (zIn % 26) + restOffset
    w - restOffset = zIn % 26
    zRest = w - restOffset

    Where zRest is the remainder of zIn % 26 to ensure w = x1. Then we use zRest below as follows:

    zRequired = ((zIn / 26) * ((25 * 0) + 1)) + ((w + divOffset) * 0)
    zRequired = (zIn / 26) * 1
    zIn = zRequired * 26 + zRest

    In case resultOffset > 0, it is not possible to have x1 == w (x1 will always be below 0, while
    w is in 1-9). Therefore, the formula will be rewritten as follows (note divisor will be 1):

    x2 = 1

    zRequired = ((zIn / 1) * ((25 * 1) + 1) + ((w + divOffset) * 1)
    zRequired = (zIn * 26) + (w + divOffset)
    zRequired - w - divOffset = zIn * 26
    zIn = (zRequired - w - divOffset) / 26

    Note that we also need to verify that (zRequired - w - divOffset) is divisible by 26, because
    the truncating of dividing by 26 could otherwise give us multiple possible values, while only
    values exactly divisible by 26 are valid here.
 */
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
    return findModelNr(computeFunctions, (1..9))
}
