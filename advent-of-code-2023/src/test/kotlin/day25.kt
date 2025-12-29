import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day25aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(54, day25a(listOf(
            "jqt: rhn xhk nvd",
            "rsh: frs pzl lsr",
            "xhk: hfx",
            "cmg: qnr nvd lhk bvb",
            "rhn: xhk bvb hfx",
            "bvb: xhk hfx",
            "pzl: lsr hfx nvd",
            "qnr: nvd",
            "ntq: jqt hfx bvb xhk",
            "nvd: lhk",
            "lsr: lhk",
            "rzs: qnr cmg lsr rsh",
            "frs: qnr lhk lsr")))
    }

    @Test
    fun testActualInput() {
        assertEquals(598120, day25a(readInputLines("day25")))
    }
}
