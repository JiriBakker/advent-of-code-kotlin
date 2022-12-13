// import PacketList.Companion.parse
//
// fun day13a(input: List<String>): Int {
//     val results =
//         input.chunked(3).map { it.take(2) }.map { (line1, line2) ->
//             val packet1 = parse(line1.drop(1).dropLast(1)).first
//             val packet2 = parse(line2.drop(1).dropLast(1)).first
//
//             val result = comparePackets(packet1, packet2)
//
//             result to (packet1 to packet2)
//         }
//
//     return results.mapIndexed { index, result ->
//         if (result.first) index + 1
//         else 0
//     }.sum()
// }
//
// private fun comparePackets(packet1: PacketList, packet2: PacketList): Boolean {
//     if (packet1 is SingleNumber && packet2 is SingleNumber) {
//         return packet1.nr <= packet2.nr
//     }
//     if (packet1 is SingleNumber) {
//         val packet2SingleNr = packet2.getFirstSingleNumber()
//         return packet2SingleNr != null && packet1.nr <= packet2SingleNr
//     }
//     if (packet2 is SingleNumber) {
//         val packet1SingleNr = packet1.getFirstSingleNumber()
//         return packet1SingleNr == null || packet1SingleNr <= packet2.nr
//     }
//     if (packet1.children.size > packet2.children.size) {
//         return false
//     }
//
//     packet1.children.forEachIndexed { index, packet1Child ->
//         val packet2Child = packet2.children[index]
//         if (!comparePackets(packet1Child, packet2Child)) {
//             return false
//         }
//     }
//
//     return true
// }
//
//
//
// fun day13b(input: List<String>): Long {
//     return 0L
// }
//
// open class PacketList(var children: List<PacketList> = mutableListOf()) {
//
//     fun getFirstSingleNumber(): Int? {
//         var cur: PacketList? = this
//         while (cur !is SingleNumber && cur != null) {
//             cur = cur.children.firstOrNull()
//         }
//         return (cur as? SingleNumber)?.nr
//     }
//
//     companion object {
//         fun parse(input: String): Pair<PacketList, Int> {
//             val children = mutableListOf<PacketList>()
//             var index = 0
//             while (index in input.indices) {
//                 if (input[index].isDigit()) {
//                     val nr = input.drop(index).takeWhile { it.isDigit() }
//                     children.add(SingleNumber(nr.toInt()))
//                     index += nr.length
//                 } else if (input[index] == '[') {
//                     val (nestedChildren, lengthParsed) = parse(input.drop(index + 1))
//                     children.add(nestedChildren)
//                     index += lengthParsed + 2
//                 } else if (input[index] == ']') {
//                     break
//                 } else {
//                     index++
//                 }
//             }
//             return PacketList(children) to index
//         }
//     }
// }
//
// class SingleNumber(val nr: Int) : PacketList()
