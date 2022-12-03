import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day16aTests {

     @Test
    fun testExampleInput1() {
        assertEquals(6, day16a(listOf(
            "D2FE28"
        )))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(9, day16a(listOf(
            "38006F45291200"
        )))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(14, day16a(listOf(
            "EE00D40C823060"
        )))
    }


    @Test
    fun testExampleInput4() {
        assertEquals(16 , day16a(listOf(
            "8A004A801A8002F478"
        )))
    }

    @Test
    fun testExampleInput5() {
        assertEquals(12, day16a(listOf(
            "620080001611562C8802118E34"
        )))
    }

    @Test
    fun testExampleInput6() {
        assertEquals(23 , day16a(listOf(
            "C0015000016115A2E0802F182340"
        )))
    }

    @Test
    fun testExampleInput7() {
        assertEquals(31, day16a(listOf(
            "A0016C880162017C3686B18A3D4780"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(1007, day16a(readInputLines("day16", 2021)))
    }
}

class Day16bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3, day16b(listOf(
            "C200B40A82"
        )))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(54, day16b(listOf(
            "04005AC33890"
        )))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(7, day16b(listOf(
            "880086C3E88112"
        )))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(9, day16b(listOf(
            "CE00C43D881120"
        )))
    }

    @Test
    fun testExampleInput5() {
        assertEquals(1, day16b(listOf(
            "D8005AC2A8F0"
        )))
    }

    @Test
    fun testExampleInput6() {
        assertEquals(0, day16b(listOf(
            "F600BC2D8F"
        )))
    }

    @Test
    fun testExampleInput7() {
        assertEquals(0, day16b(listOf(
            "9C005AC2F8F0"
        )))
    }

    @Test
    fun testExampleInput8() {
        assertEquals(1, day16b(listOf(
            "9C0141080250320F1802104A08"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(834151779165, day16b(readInputLines("day16", 2021)))
    }
}
