import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day23aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(7, day23a(listOf(
            "kh-tc",
            "qp-kh",
            "de-cg",
            "ka-co",
            "yn-aq",
            "qp-ub",
            "cg-tb",
            "vc-aq",
            "tb-ka",
            "wh-tc",
            "yn-cg",
            "kh-ub",
            "ta-co",
            "de-co",
            "tc-td",
            "tb-wq",
            "wh-td",
            "ta-ka",
            "td-qp",
            "aq-cg",
            "wq-ub",
            "ub-vc",
            "de-ta",
            "wq-aq",
            "wq-vc",
            "wh-yn",
            "ka-de",
            "kh-ta",
            "co-tc",
            "wh-qp",
            "tb-vc",
            "td-yn")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1248, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test
    fun testExampleInput1() {
        assertEquals("co,de,ka,ta", day23b(listOf(
            "kh-tc",
            "qp-kh",
            "de-cg",
            "ka-co",
            "yn-aq",
            "qp-ub",
            "cg-tb",
            "vc-aq",
            "tb-ka",
            "wh-tc",
            "yn-cg",
            "kh-ub",
            "ta-co",
            "de-co",
            "tc-td",
            "tb-wq",
            "wh-td",
            "ta-ka",
            "td-qp",
            "aq-cg",
            "wq-ub",
            "ub-vc",
            "de-ta",
            "wq-aq",
            "wq-vc",
            "wh-yn",
            "ka-de",
            "kh-ta",
            "co-tc",
            "wh-qp",
            "tb-vc",
            "td-yn")))
    }


    @Test
    fun testActualInput() {
        assertEquals("aa,cf,cj,cv,dr,gj,iu,jh,oy,qr,xr,xy,zb", day23b(readInputLines("day23")))
    }
}
