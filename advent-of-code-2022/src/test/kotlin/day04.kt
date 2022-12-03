import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day04aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day04a(listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day04a(readInputLines("day04")))
    }
}

class Day04bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(0, day04b(listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw")))
    }

    @Test
    fun testActualInput() {
        assertEquals(0, day04b(readInputLines("day04")))
    }
}
