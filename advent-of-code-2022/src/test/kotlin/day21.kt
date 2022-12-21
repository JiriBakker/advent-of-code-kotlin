import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day21aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(152, day21a(listOf(
            "root: pppw + sjmn",
            "dbpl: 5",
            "cczh: sllz + lgvd",
            "zczc: 2",
            "ptdq: humn - dvpt",
            "dvpt: 3",
            "lfqf: 4",
            "humn: 5",
            "ljgn: 2",
            "sjmn: drzm * dbpl",
            "sllz: 4",
            "pppw: cczh / lfqf",
            "lgvd: ljgn * ptdq",
            "drzm: hmdt - zczc",
            "hmdt: 32")))
    }

    @Test
    fun testActualInput() {
        assertEquals(291425799367130L, day21a(readInputLines("day21")))
    }
}

class Day21bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(301, day21b(listOf(
            "root: pppw + sjmn",
            "dbpl: 5",
            "cczh: sllz + lgvd",
            "zczc: 2",
            "ptdq: humn - dvpt",
            "dvpt: 3",
            "lfqf: 4",
            "humn: 5",
            "ljgn: 2",
            "sjmn: drzm * dbpl",
            "sllz: 4",
            "pppw: cczh / lfqf",
            "lgvd: ljgn * ptdq",
            "drzm: hmdt - zczc",
            "hmdt: 32")))
    }

    @Test
    fun testActualInput() {
        assertEquals(3219579395609L, day21b(readInputLines("day21")))
    }
}
