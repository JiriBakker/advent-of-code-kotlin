package v2017.days.day12

private fun groupPrograms(connections: List<String>): Pair<Map<Int, Int>, Map<Int, List<Int>>> {
    val programToGroupLookup = mutableMapOf<Int, Int>()
    val groupToProgramLookup = mutableMapOf<Int, MutableList<Int>>()

    fun mergeGroups(groupId1: Int, groupId2: Int) {
        val programsToMove = groupToProgramLookup[groupId2]!!
        groupToProgramLookup.remove(groupId2)
        groupToProgramLookup.putIfAbsent(groupId1, mutableListOf())
        groupToProgramLookup[groupId1]!!.addAll(programsToMove)
        programsToMove.forEach { programToGroupLookup[it] = groupId1 }
    }

    fun addToGroup(programId: Int, groupId: Int) {
        programToGroupLookup[programId] = groupId
        groupToProgramLookup.putIfAbsent(groupId, mutableListOf())
        groupToProgramLookup[groupId]!!.add(programId)
    }

    connections.forEach { line ->
        val (program, connectedCsv) = line.split(" <-> ")
        val programIds = connectedCsv.split(", ").plus(program).map(String::toInt)

        val groupId = programToGroupLookup[programIds.first()] ?: programToGroupLookup.size

        programIds.forEach { programId ->
            val otherGroupId = programToGroupLookup[programId]
            if (otherGroupId == null) {
                addToGroup(programId, groupId)
            } else {
                mergeGroups(groupId, otherGroupId)
            }
        }
    }

    return programToGroupLookup to groupToProgramLookup
}


fun day12a(input: List<String>): Int {
    val (programToGroupLookup, groupToProgramLookup) = groupPrograms(input)
    return groupToProgramLookup[programToGroupLookup[0]!!]!!.size
}

fun day12b(input: List<String>): Int {
    val (_, groupToProgramLookup) = groupPrograms(input)
    return groupToProgramLookup.size
}
