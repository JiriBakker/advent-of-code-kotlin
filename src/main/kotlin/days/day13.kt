package days.day13

import java.util.PriorityQueue

private enum class IntersectionDecision {
    GO_LEFT,
    GO_STRAIGHT,
    GO_RIGHT
}

private enum class CartDirection {
    LEFT,
    UP,
    RIGHT,
    DOWN
}

private enum class TrackCellType {
    HORIZONTAL,
    VERTICAL,
    INTERSECTION,
    TOPLEFT_BOTTOMRIGHT,
    BOTTOMLEFT_TOPRIGHT
}

private class TrackCell(val type: TrackCellType, var cart: Cart?)

private data class Pos(val x: Int, val y: Int)

private class Cart(initialX: Int, initialY: Int, initialDirection: CartDirection) {
    var x = initialX
    var y = initialY
    var direction = initialDirection

    private var lastIntersectionDecision: IntersectionDecision? = null

    private fun getIntersectionDecision(): IntersectionDecision {
        lastIntersectionDecision = when (lastIntersectionDecision) {
            IntersectionDecision.GO_LEFT -> IntersectionDecision.GO_STRAIGHT
            IntersectionDecision.GO_STRAIGHT -> IntersectionDecision.GO_RIGHT
            else -> IntersectionDecision.GO_LEFT
        }
        return lastIntersectionDecision!!
    }

    private fun rotateLeft() {
        direction = when (direction) {
            CartDirection.LEFT -> CartDirection.DOWN
            CartDirection.UP -> CartDirection.LEFT
            CartDirection.RIGHT -> CartDirection.UP
            CartDirection.DOWN -> CartDirection.RIGHT
        }
    }

    private fun rotateRight() {
        direction = when (direction) {
            CartDirection.LEFT -> CartDirection.UP
            CartDirection.UP -> CartDirection.RIGHT
            CartDirection.RIGHT -> CartDirection.DOWN
            CartDirection.DOWN -> CartDirection.LEFT
        }
    }

    fun moveTo(x: Int, y: Int, cellType: TrackCellType) {
        this.x = x
        this.y = y

        when (cellType) {
            TrackCellType.INTERSECTION -> {
                when (getIntersectionDecision()) {
                    IntersectionDecision.GO_LEFT -> rotateLeft()
                    IntersectionDecision.GO_RIGHT -> rotateRight()
                    else -> Unit
                }
            }
            TrackCellType.TOPLEFT_BOTTOMRIGHT -> {
                when (direction) {
                    CartDirection.RIGHT -> rotateRight()
                    CartDirection.UP -> rotateLeft()
                    CartDirection.LEFT -> rotateRight()
                    CartDirection.DOWN -> rotateLeft()
                }
            }
            TrackCellType.BOTTOMLEFT_TOPRIGHT -> {
                when (direction) {
                    CartDirection.RIGHT -> rotateLeft()
                    CartDirection.UP -> rotateRight()
                    CartDirection.LEFT -> rotateLeft()
                    CartDirection.DOWN -> rotateRight()
                }
            }
            else -> Unit
        }
    }
}

private class Tracks private constructor(private val grid: Array<Array<TrackCell?>>, private val carts: PriorityQueue<Cart>) {
    companion object {
        fun parse(inputLines: List<String>): Tracks {
            val height = inputLines.size
            val width = inputLines.map { it.length }.max()!!

            val grid: Array<Array<TrackCell?>> = Array(width) { Array<TrackCell?>(height) { null } }
            val carts = PriorityQueue<Cart> { a, b -> (a.y * height + a.x).compareTo(b.y * height + b.x) }

            for (x in 0 until width) {
                for (y in 0 until height) {
                    grid[x][y] = when (inputLines[y].getOrNull(x)) {
                        '|' -> TrackCell(TrackCellType.VERTICAL, null)
                        '-' -> TrackCell(TrackCellType.HORIZONTAL, null)
                        '+' -> TrackCell(TrackCellType.INTERSECTION, null)
                        '\\' -> TrackCell(TrackCellType.TOPLEFT_BOTTOMRIGHT, null)
                        '/' -> TrackCell(TrackCellType.BOTTOMLEFT_TOPRIGHT, null)
                        '>' -> {
                            val cart = Cart(x, y, CartDirection.RIGHT)
                            carts.offer(cart)
                            TrackCell(TrackCellType.HORIZONTAL, cart)
                        }
                        '<' -> {
                            val cart = Cart(x, y, CartDirection.LEFT)
                            carts.offer(cart)
                            TrackCell(TrackCellType.HORIZONTAL, cart)
                        }
                        '^' -> {
                            val cart = Cart(x, y, CartDirection.UP)
                            carts.offer(cart)
                            TrackCell(TrackCellType.VERTICAL, cart)
                        }
                        'v' -> {
                            val cart = Cart(x, y, CartDirection.DOWN)
                            carts.offer(cart)
                            TrackCell(TrackCellType.VERTICAL, cart)
                        }
                        else -> null
                    }
                }
            }

            return Tracks(grid, carts)
        }
    }

    fun print() {
        for (y in 0 until grid[0].size) {
            for (x in 0 until grid.size) {
                val cell = grid[x][y]
                when {
                    cell == null -> print(" ")
                    cell!!.cart != null ->
                        print(when (cell!!.cart!!.direction) {
                            CartDirection.LEFT -> '<'
                            CartDirection.UP -> '^'
                            CartDirection.RIGHT -> '>'
                            CartDirection.DOWN -> 'v'
                        })
                    else ->
                        print(when (cell!!.type) {
                            TrackCellType.HORIZONTAL -> '-'
                            TrackCellType.VERTICAL -> '|'
                            TrackCellType.INTERSECTION -> '+'
                            TrackCellType.TOPLEFT_BOTTOMRIGHT -> '\\'
                            TrackCellType.BOTTOMLEFT_TOPRIGHT -> '/'
                        })
                }
            }
            println()
        }
    }

    fun iterateCarts(shouldStopIteratingOnCrash: Boolean): Pos? {
        while (carts.size > 1) {
            val movedCarts: MutableList<Cart> = mutableListOf()

            while (carts.isNotEmpty()) {
                val cart = carts.poll()
                val curCell = grid[cart.x][cart.y]!!
                grid[cart.x][cart.y] = TrackCell(curCell.type, null)

                val (nextX, nextY) = when (cart.direction) {
                    CartDirection.LEFT -> Pos(cart.x - 1, cart.y)
                    CartDirection.UP -> Pos(cart.x, cart.y - 1)
                    CartDirection.RIGHT -> Pos(cart.x + 1, cart.y)
                    CartDirection.DOWN -> Pos(cart.x, cart.y + 1)
                }!!

                val nextCell = grid[nextX][nextY]!!
                if (nextCell.cart != null) {
                    if (shouldStopIteratingOnCrash) {
                        return Pos(nextX, nextY)
                    }

                    val otherCart = nextCell.cart
                    carts.remove(otherCart)
                    movedCarts.remove(otherCart)
                    grid[nextX][nextY] = TrackCell(nextCell.type, null)
                    grid[cart.x][cart.y] = TrackCell(curCell.type, null)
                } else {
                    cart.moveTo(nextX, nextY, nextCell.type)
                    grid[nextX][nextY] = TrackCell(nextCell.type, cart)
                    movedCarts.add(cart)
                }
            }

            carts.addAll(movedCarts)
        }

        if (carts.isEmpty()) {
            return null
        }
        val lastCart = carts.poll()!!
        return Pos(lastCart.x, lastCart.y)
    }
}

fun day13a(inputLines: List<String>): Pair<Int, Int>? {
    val tracks = Tracks.parse(inputLines)
    val pos = tracks.iterateCarts(true)
    return if (pos != null) Pair(pos.x, pos.y) else null
}

fun day13b(inputLines: List<String>): Pair<Int, Int>? {
    val tracks = Tracks.parse(inputLines)
    val pos = tracks.iterateCarts(false)
    return if (pos != null) Pair(pos.x, pos.y) else null
}
