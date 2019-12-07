package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day07.day07a

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals(43210, day07a("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"))
    }

    @Test fun testExampleInput2() {
        assertEquals(54321, day07a("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"))
    }

    @Test fun testExampleInput3() {
        assertEquals(65210, day07a("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"))
    }

    @Test fun testActualInput() {
        assertEquals(7988899, day07a(readInputLine("day07")))
    }
}
//
// class Day05bTests {
//     @Test fun testExampleInput1() {
//         assertEquals(999, day05b(
//             "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"))
//     }
//     @Test fun testExampleInput2() {
//         assertEquals(1000, day05b(
//             "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
//             "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
//             "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", 8))
//     }
//     @Test fun testExampleInput3() {
//         assertEquals(1001, day05b(
//             "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
//             "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
//             "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", 9))
//     }
//
//     @Test fun testActualInput() {
//         assertEquals(13758663, day05b(readInputLine("day05"), 5))
//     }
// }