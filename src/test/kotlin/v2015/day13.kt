package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day13.day13a
import v2015.days.day13.day13b
import v2015.util.readInputLines

class Day13aTests {
    @Test fun testExampleInput1() {
        assertEquals(330, day13a(listOf(
            "Alice would gain 54 happiness units by sitting next to Bob.",
            "Alice would lose 79 happiness units by sitting next to Carol.",
            "Alice would lose 2 happiness units by sitting next to David.",
            "Bob would gain 83 happiness units by sitting next to Alice.",
            "Bob would lose 7 happiness units by sitting next to Carol.",
            "Bob would lose 63 happiness units by sitting next to David.",
            "Carol would lose 62 happiness units by sitting next to Alice.",
            "Carol would gain 60 happiness units by sitting next to Bob.",
            "Carol would gain 55 happiness units by sitting next to David.",
            "David would gain 46 happiness units by sitting next to Alice.",
            "David would lose 7 happiness units by sitting next to Bob.",
            "David would gain 41 happiness units by sitting next to Carol.")))
    }

    @Test fun testActualInput() {
        assertEquals(664, day13a(readInputLines("day13")))
    }
}

class Day13bTests {
    @Test fun testActualInput() {
        assertEquals(640, day13b(readInputLines("day13")))
    }
}


