package v2017

private class BufferValue(var value: Int, var next: BufferValue? = null)

fun day17a(input: String): Int {
    val steps = input.toInt()

    var curValue = BufferValue(0)
    curValue.next = curValue

    (1 .. 2017).forEach { nr ->
        repeat(steps % nr) { curValue = curValue.next!! }
        curValue.next = BufferValue(nr, curValue.next)
        curValue = curValue.next!!
    }

    return curValue.next!!.value
}

fun day17b(input: String): Int {
    val steps = input.toInt()

    var lastIterationNrWithIndex0 = 0
    var index = 0
    (1 .. 50000000).forEach { nr ->
        index = (index + steps) % nr
        if (index == 0) {
            lastIterationNrWithIndex0 = nr
        }
        index++
    }
    return lastIterationNrWithIndex0
}