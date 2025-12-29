import com.microsoft.z3.*

fun day24a(input: List<String>, allowedRange: ClosedFloatingPointRange<Double>): Int {
    val hailstones = parseHailstones(input)

    return hailstones.indices.sumOf { i ->
        (i + 1 until hailstones.size).count { j ->
            val hailstone1 = hailstones[i]
            val hailstone2 = hailstones[j]

            val intersectionPos = hailstone1.find2DIntersectionWith(hailstone2, allowedRange)

            if (intersectionPos != null) {
                val (intersectX, _) = intersectionPos
                val intersectTime1 = (intersectX - hailstone1.px) / hailstone1.vx
                val intersectTime2 = (intersectX - hailstone2.px) / hailstone2.vx

                intersectTime1 >= 0 && intersectTime2 >= 0
            } else {
                false
            }
        }
    }
}

fun day24b(input: List<String>): Long {
    val hailstones = parseHailstones(input)

    Context().use { ctx ->
        val solver = ctx.mkSolver()

        val x = ctx.mkRealConst("x")
        val y = ctx.mkRealConst("y")
        val z = ctx.mkRealConst("z")
        val vx = ctx.mkRealConst("vx")
        val vy = ctx.mkRealConst("vy")
        val vz = ctx.mkRealConst("vz")

        hailstones.take(3).withIndex().forEach { (index, h) ->
            val t = ctx.mkRealConst("time$index")
            solver.add(
                ctx.mkEq(
                    ctx.mkAdd(x, ctx.mkMul(vx, t)),
                    ctx.mkAdd(ctx.mkReal(h.px.toLong()), ctx.mkMul(ctx.mkReal(h.vx.toLong()), t))
                )
            )
            solver.add(
                ctx.mkEq(
                    ctx.mkAdd(y, ctx.mkMul(vy, t)),
                    ctx.mkAdd(ctx.mkReal(h.py.toLong()), ctx.mkMul(ctx.mkReal(h.vy.toLong()), t))
                )
            )
            solver.add(
                ctx.mkEq(
                    ctx.mkAdd(z, ctx.mkMul(vz, t)),
                    ctx.mkAdd(ctx.mkReal(h.pz.toLong()), ctx.mkMul(ctx.mkReal(h.vz.toLong()), t))
                )
            )
        }

        if (solver.check() != Status.SATISFIABLE) throw Exception("No solution found")

        return solver.model.eval(ctx.mkAdd(x, ctx.mkAdd(y, z)), false).let { result -> result as RatNum }.toDecimalString(1).toLong()
    }
}

private data class Hailstone(
    val px: Double,
    val py: Double,
    val pz: Double,
    val vx: Double,
    val vy: Double,
    val vz: Double
) {
    private fun getStandardForm(): Triple<Double, Double, Double> {
        val start = (this.px to this.py)
        val end = (this.px + 10 * this.vx to this.py + 10 * this.vy)
        val a = (start.second - end.second)
        val b = (start.first - end.first)
        val c = (end.first * start.second - start.first * end.second)
        return Triple(a, -b, c)
    }

    fun find2DIntersectionWith(other: Hailstone, allowedRange: ClosedFloatingPointRange<Double>): Pair<Double, Double>? {
        val (a1, b1, c1) = this.getStandardForm()
        val (a2, b2, c2) = other.getStandardForm()
        val d = a1 * b2 - b1 * a2
        if (d == 0.0) return null
        val dx = c1 * b2 - b1 * c2
        val dy = a1 * c2 - c1 * a2
        val x = dx / d
        val y = dy / d

        if (x in allowedRange && y in allowedRange) {
            return x to y
        }
        return null
    }
}

private fun parseHailstones(input: List<String>): List<Hailstone> {
    return input.map { line ->
        val (px, py, pz) = line.split(" @ ").first().split(", ").map(String::trim).map(String::toDouble)
        val (vx, vy, vz) = line.split(" @ ").last().split(", ").map(String::trim).map(String::toDouble)
        Hailstone(px, py, pz, vx, vy, vz)
    }
}