import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines



class Day18aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(29, day18a(listOf(
            "[9,1]"
        )))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(21, day18a(listOf(
            "[1,9]"
        )))
    }

    @Test
    fun testExampleInput3() {
        assertEquals(129, day18a(listOf(
            "[[9,1],[1,9]]"
        )))
    }

    @Test
    fun testExampleInput4() {
        assertEquals(4140, day18a(listOf(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(4365, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(3993, day18b(listOf(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(4490, day18b(readInputLines("day18")))
    }
}
