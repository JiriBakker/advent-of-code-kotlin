import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day03aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(157, day03a(listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw")))
    }

    @Test
    fun testActualInput() {
        assertEquals(7766, day03a(readInputLines("day03", 2022)))
    }
}

class Day03bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(70, day03b(listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw")))
    }

    @Test
    fun testActualInput() {
        assertEquals(2415, day03b(readInputLines("day03", 2022)))
    }
}
