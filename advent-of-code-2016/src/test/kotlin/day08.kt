import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day08aTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day08a(listOf(
            "rect 3x2",
            "rotate column x=1 by 1",
            "rotate row y=0 by 4",
            "rotate column x=1 by 1"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(119, day08a(readInputLines("day08", 2016)))
    }
}

class Day08bTests {
    @Test fun testActualInput() {
        assertEquals("" +
            "████ ████ █  █ ████  ███ ████  ██   ██  ███   ██            \n" +
            "   █ █    █  █ █    █    █    █  █ █  █ █  █ █  █           \n" +
            "  █  ███  ████ ███  █    ███  █  █ █    █  █ █  █           \n" +
            " █   █    █  █ █     ██  █    █  █ █ ██ ███  █  █           \n" +
            "█    █    █  █ █       █ █    █  █ █  █ █    █  █           \n" +
            "████ █    █  █ █    ███  █     ██   ███ █     ██            \n",
            day08b(readInputLines("day08", 2016)))
    }
}
