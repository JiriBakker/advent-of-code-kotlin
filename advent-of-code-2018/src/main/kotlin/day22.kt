import java.util.PriorityQueue

import Day22.Pos

private enum class RegionType {
    Rocky,
    Wet,
    Narrow
}

private enum class EquippedTool {
    Torch,
    ClimbingGear,
    Neither
}

private object Day22 {
    class Pos(val x: Int, val y: Int)
}

private class Region(val x: Int, val y: Int, val erosionLevel: Long, val type: RegionType)

private class RouteNode(val region: Region, var equippedTool: EquippedTool, var costToReach: Int = Int.MAX_VALUE) {
    override fun hashCode(): Int {
        return this.region.y * 10000 + this.region.x * 10 + equippedTool.ordinal
    }
}

private class Cave(val depth: Int, val targetPos: Pos) {
    private val regions: MutableMap<Int, MutableMap<Int, Region>> = mutableMapOf()
    private val routeNodes: MutableMap<Int, MutableMap<Int, MutableMap<EquippedTool, RouteNode>>> = mutableMapOf()

    init {
        val startRegion = createRegion(0, 0)
        regions[0] = mutableMapOf(Pair(0, startRegion))
        val startRouteNode = getRouteNode(startRegion, EquippedTool.Torch)
        startRouteNode.costToReach = 0
    }

    private fun createRegion(x: Int, y: Int): Region {
        val geologicIndex = when {
            y == targetPos.y && x == targetPos.x -> 0
            y == 0 -> x * 16807L
            x == 0 -> y * 48271L
            else -> {
                val regionLeft = getRegion(x - 1, y)
                val regionAbove = getRegion(x, y - 1)
                regionLeft.erosionLevel * regionAbove.erosionLevel
            }
        }

        val erosionLevel = (geologicIndex + depth) % 20183
        val regionType = when (erosionLevel % 3) {
            0L -> RegionType.Rocky
            1L -> RegionType.Wet
            else -> RegionType.Narrow
        }
        return Region(x, y, erosionLevel, regionType)
    }

    fun getRegion(x: Int, y: Int): Region {
        val row = regions.getOrPut(y, ::mutableMapOf)
        return row.getOrPut(x) { createRegion(x, y) }
    }

    fun getRouteNode(region: Region, equippedTool: EquippedTool): RouteNode {
        val row = routeNodes.getOrPut(region.y) { mutableMapOf() }
        val costs = row.getOrPut(region.x) { mutableMapOf() }
        costs[equippedTool] = costs.getOrElse(equippedTool) { RouteNode(region, equippedTool) }
        return costs[equippedTool]!!
    }
}

private fun parse(inputLines: List<String>): Triple<Int, Int, Int> {
    val depth = inputLines[0].substring(7).toInt()
    val (targetX, targetY) = inputLines[1].substring(8).split(',').map(String::toInt)
    return Triple(depth, targetX, targetY)
}

private fun getReachableNeighbours(routeNode: RouteNode, cave: Cave): List<RouteNode> {
    val reachableNeighbours: MutableSet<RouteNode> = mutableSetOf()

    fun addNeighbour(neighbour: Region, equippedTool: EquippedTool, addedCost: Int) {
        val neighbourRouteNode = cave.getRouteNode(neighbour, equippedTool)
        neighbourRouteNode.costToReach =
            Math.min(
                neighbourRouteNode.costToReach,
                routeNode.costToReach + addedCost
            )
        reachableNeighbours.add(neighbourRouteNode)
    }

    fun addReachable(x: Int, y: Int) {
        val neighbour = cave.getRegion(x, y)
        when (routeNode.region.type) {
            RegionType.Rocky -> {
                when (neighbour.type) {
                    RegionType.Rocky -> {
                        if (routeNode.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbour, EquippedTool.Torch, 1)
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 8)
                        } else if (routeNode.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbour, EquippedTool.Torch, 8)
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 1)
                        }
                    }
                    RegionType.Wet -> {
                        if (routeNode.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 8)
                        } else if (routeNode.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 1)
                        }
                    }
                    RegionType.Narrow -> {
                        if (routeNode.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbour, EquippedTool.Torch, 1)
                        } else if (routeNode.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbour, EquippedTool.Torch, 8)
                        }
                    }
                }
            }
            RegionType.Wet -> {
                when (neighbour.type) {
                    RegionType.Rocky -> {
                        if (routeNode.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 1)
                        } else if (routeNode.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 8)
                        }
                    }
                    RegionType.Wet -> {
                        if (routeNode.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 1)
                            addNeighbour(neighbour, EquippedTool.Neither, 8)
                        } else if (routeNode.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbour, EquippedTool.ClimbingGear, 8)
                            addNeighbour(neighbour, EquippedTool.Neither, 1)
                        }
                    }
                    RegionType.Narrow -> {
                        if (routeNode.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbour, EquippedTool.Neither, 8)
                        } else if (routeNode.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbour, EquippedTool.Neither, 1)
                        }
                    }
                }
            }
            RegionType.Narrow -> {
                when (neighbour.type) {
                    RegionType.Rocky -> {
                        if (routeNode.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbour, EquippedTool.Torch, 1)
                        } else if (routeNode.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbour, EquippedTool.Torch, 8)
                        }
                    }
                    RegionType.Wet -> {
                        if (routeNode.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbour, EquippedTool.Neither, 8)
                        } else if (routeNode.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbour, EquippedTool.Neither, 1)
                        }
                    }
                    RegionType.Narrow -> {
                        if (routeNode.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbour, EquippedTool.Torch, 1)
                            addNeighbour(neighbour, EquippedTool.Neither, 8)
                        } else if (routeNode.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbour, EquippedTool.Torch, 8)
                            addNeighbour(neighbour, EquippedTool.Neither, 1)
                        }
                    }
                }
            }
        }
    }

    if (routeNode.region.y > 0) {
        addReachable(routeNode.region.x, routeNode.region.y - 1)
    }
    if (routeNode.region.x > 0) {
        addReachable(routeNode.region.x - 1, routeNode.region.y)
    }
    addReachable(routeNode.region.x, routeNode.region.y + 1)
    addReachable(routeNode.region.x + 1, routeNode.region.y)

    return reachableNeighbours.toList()
}

fun day22a(inputLines: List<String>): Int {
    val (depth, targetX, targetY) = parse(inputLines)

    var riskFactor = 0

    val grid = Cave(depth, Pos(targetX, targetY))
    for (y in 0..targetY) {
        for (x in 0..targetX) {
            val region = grid.getRegion(x, y)
            riskFactor += when (region.type) {
                RegionType.Rocky -> 0
                RegionType.Wet -> 1
                RegionType.Narrow -> 2
            }
        }
    }
    return riskFactor
}

fun day22b(inputLines: List<String>): Int? {
    val (depth, targetX, targetY) = parse(inputLines)

    val cave = Cave(depth, Pos(targetX, targetY))
    val startRegion = cave.getRegion(0, 0)
    val startRouteNode = cave.getRouteNode(startRegion, EquippedTool.Torch)

    val toVisit: PriorityQueue<RouteNode> = PriorityQueue { p0, p1 -> p0.costToReach - p1.costToReach }
    toVisit.offer(startRouteNode)

    val seenRouteNodes: MutableSet<RouteNode> = mutableSetOf()
    seenRouteNodes.add(startRouteNode)

    while (toVisit.size > 0) {
        val currentRouteNode = toVisit.poll()
        if (currentRouteNode.region.x == targetX && currentRouteNode.region.y == targetY) {
            return currentRouteNode.costToReach + if (currentRouteNode.equippedTool != EquippedTool.Torch) 7 else 0
        }

        getReachableNeighbours(currentRouteNode, cave).forEach {
            if (!seenRouteNodes.contains(it)) {
                toVisit.add(it)
                seenRouteNodes.add(it)
            }
        }
    }
    return null
}