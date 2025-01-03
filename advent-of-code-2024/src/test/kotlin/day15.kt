import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(2028, day15a(listOf(
            "########",
            "#..O.O.#",
            "##@.O..#",
            "#...O..#",
            "#.#.O..#",
            "#...O..#",
            "#......#",
            "########",
            "",
            "<^^>>>vv<v>>v<<")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(10092, day15a(listOf(
            "##########",
            "#..O..O.O#",
            "#......O.#",
            "#.OO..O.O#",
            "#..O@..O.#",
            "#O#..O...#",
            "#O..O..O.#",
            "#.OO.O.OO#",
            "#....O...#",
            "##########",
            "",
            "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^",
            "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v",
            "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<",
            "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^",
            "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><",
            "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^",
            ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^",
            "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>",
            "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>",
            "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^")))
    }


    @Test
    fun testActualInput() {
        assertEquals(1463512, day15a(readInputLines("day15")))
    }
}

class Day15bTests {

    @Test
    fun testCustomInput1() {
        assertEquals(1028, day15b(listOf(
            "#######",
            "#.....#",
            "#...O.#",
            "#..OO.#",
            "#..O@.#",
            "#.....#",
            "#######",
            "",
            "^^")))
    }

    @Test
    fun testCustomInput2() {
        assertEquals(104, day15b(listOf(
            "#####",
            "#.O..",
            "#@...",
            "",
            "")))
    }

    @Test
    fun testCustomInput3() {
        assertEquals(1628, day15b(listOf(
            "#######",
            "#.....#",
            "#...@.#",
            "#..OO.#",
            "#..OO.#",
            "#.....#",
            "#######",
            "",
            "vv")))
    }

    @Test
    fun testCustomInput4() {
        assertEquals(1420, day15b(listOf(
            "#######",
            "#.....#",
            "#.....#",
            "#.OO@.#",
            "#..OO.#",
            "#.....#",
            "#######",
            "",
            "<<<")))
    }

    @Test
    fun testCustomInput5() {
        assertEquals(1212, day15b(listOf(
            "#######",
            "#.....#",
            "#.....#",
            "#.OO@.#",
            "#.O...#",
            "#.....#",
            "#######",
            "",
            "<^<v")))
    }

    @Test
    fun testCustomInput6() {
        assertEquals(1224, day15b(listOf(
            "#######",
            "#.....#",
            "#.....#",
            "#.@OO.#",
            "#...O.#",
            "#.....#",
            "#######",
            "",
            ">>^>>>vv")))
    }

    @Test
    fun testCustomInput7() {
        assertEquals(1024, day15b(listOf(
            "#######",
            "#.....#",
            "#..#..#",
            "#.@OO.#",
            "#...O.#",
            "#.....#",
            "#######",
            "",
            ">>v>^")))
    }

    @Test
    fun testExampleInput1() {
        assertEquals(618, day15b(listOf(
            "#######",
            "#...#.#",
            "#.....#",
            "#..OO@#",
            "#..O..#",
            "#.....#",
            "#######",
            "",
            "<vv<<^^<<^^")))
    }

    @Test
    fun testExampleInput2() {
        assertEquals(9021, day15b(listOf(
            "##########",
            "#..O..O.O#",
            "#......O.#",
            "#.OO..O.O#",
            "#..O@..O.#",
            "#O#..O...#",
            "#O..O..O.#",
            "#.OO.O.OO#",
            "#....O...#",
            "##########",
            "",
            "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^",
            "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v",
            "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<",
            "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^",
            "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><",
            "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^",
            ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^",
            "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>",
            "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>",
            "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1486520, day15b(readInputLines("day15")))
    }
}
