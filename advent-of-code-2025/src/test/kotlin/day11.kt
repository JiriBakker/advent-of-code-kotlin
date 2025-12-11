import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day11aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(5, day11a(listOf(
            "aaa: you hhh",
            "you: bbb ccc",
            "bbb: ddd eee",
            "ccc: ddd eee fff",
            "ddd: ggg",
            "eee: out",
            "fff: out",
            "ggg: out",
            "hhh: ccc fff iii",
            "iii: out")))
    }

    @Test
    fun testActualInput() {
        assertEquals(690, day11a(readInputLines("day11")))
    }
}

class Day11bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2, day11b(listOf(
            "svr: aaa bbb",
            "aaa: fft",
            "fft: ccc",
            "bbb: tty",
            "tty: ccc",
            "ccc: ddd eee",
            "ddd: hub",
            "hub: fff",
            "eee: dac",
            "dac: fff",
            "fff: ggg hhh",
            "ggg: out",
            "hhh: out")))
    }

    @Test
    fun testActualInput() {
        assertEquals(557332758684000, day11b(readInputLines("day11")))
    }

}
