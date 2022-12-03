import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals("CABDFE", day07a(
            listOf(
                "Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin."
            )
        )
        )
    }

    @Test fun testCustomInput1() {
        assertEquals("ABC", day07a(
            listOf(
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step C can begin."
            )
        )
        )
    }

    @Test fun testCustomInput2() {
        assertEquals("ABCD", day07a(
            listOf(
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step C can begin.",
                "Step C must be finished before step D can begin."
            )
        )
        )
    }

    @Test fun testCustomInput3() {
        assertEquals("ABCDE", day07a(
            listOf(
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step C can begin.",
                "Step C must be finished before step D can begin.",
                "Step D must be finished before step E can begin.",
                "Step B must be finished before step E can begin."
            )
        )
        )
    }

    @Test fun testCustomInput4() {
        assertEquals("ACDEB", day07a(
            listOf(
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step C can begin.",
                "Step C must be finished before step D can begin.",
                "Step D must be finished before step E can begin.",
                "Step E must be finished before step B can begin."
            )
        )
        )
    }

    @Test fun testActualInput() {
        assertEquals("CFMNLOAHRKPTWBJSYZVGUQXIDE", day07a(readInputLines("day07", 2018)))
    }
}

class Day07bTests {
    @Test fun testExampleInput1() {
        assertEquals(15, day07b(
            listOf(
                "Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin."
            ), 2, 0
        )
        )
    }

    @Test fun testActualInput() {
        assertEquals(971, day07b(readInputLines("day07", 2018)))
    }
}
