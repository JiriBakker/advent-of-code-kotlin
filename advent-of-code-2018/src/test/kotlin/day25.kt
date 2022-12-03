import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(2,
            day25a(
                listOf(
                    "0,0,0,0",
                    "3,0,0,0",
                    "0,3,0,0",
                    "0,0,3,0",
                    "0,0,0,3",
                    "0,0,0,6",
                    "9,0,0,0",
                    "12,0,0,0"
                )
            )
        )
    }

    @Test fun testExampleInput2() {
        assertEquals(4,
            day25a(
                listOf(
                    "-1,2,2,0",
                    "0,0,2,-2",
                    "0,0,0,-2",
                    "-1,2,0,0",
                    "-2,-2,-2,2",
                    "3,0,2,-1",
                    "-1,3,2,2",
                    "-1,0,-1,0",
                    "0,2,1,-2",
                    "3,0,0,0"
                )
            )
        )
    }

    @Test fun testExampleInput3() {
        assertEquals(3,
            day25a(
                listOf(
                    "1,-1,0,1",
                    "2,0,-1,0",
                    "3,2,-1,0",
                    "0,0,3,1",
                    "0,0,-1,-1",
                    "2,3,-2,0",
                    "-2,2,0,0",
                    "2,-2,0,-1",
                    "1,-1,0,-1",
                    "3,2,0,2"
                )
            )
        )
    }

    @Test fun testExampleInput4() {
        assertEquals(8,
            day25a(
                listOf(
                    "1,-1,-1,-2",
                    "-2,-2,0,1",
                    "0,2,1,3",
                    "-2,3,-2,1",
                    "0,2,3,-2",
                    "-1,-1,1,-2",
                    "0,-2,-1,0",
                    "-2,2,3,-1",
                    "1,2,2,0",
                    "-1,-2,0,-2"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(350,
            day25a(readInputLines("day25", 2018))
        )
    }
}