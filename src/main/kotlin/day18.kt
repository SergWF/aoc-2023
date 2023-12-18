import Direction.D
import Direction.L
import Direction.R
import Direction.U
import kotlin.math.abs

fun day18_1(lines: List<String>): Int = lines.toTrenchPlan().calcArea()

fun day18_2(lines: List<String>): Int = lines.also { printData(it) }.count()

const val trenchWidth = 1

fun List<String>.toTrenchPlan(): List<Position> =
    this.fold(mutableListOf(Position(0, 0))) { acc, line ->
        val s = line.split(" ")
        val dir: Direction = Direction.valueOf(s[0])
        val len = s[1].toInt()
        val rgb = s[2]
        val lastPos = acc.last()
        val nextPos = when (dir) {
            D -> Position(lastPos.line + len, lastPos.col)
            U -> Position(lastPos.line - len, lastPos.col)
            L -> Position(lastPos.line, lastPos.col - len)
            R -> Position(lastPos.line, lastPos.col + len)
        }
        acc.add(nextPos)
        acc
    }

fun List<Position>.calcPerimeter(): Int = (1..this.lastIndex).map { idx -> this[idx - 1].distance(this[idx]) }.sum()

private fun Position.distance(pos: Position): Int = abs(this.line - pos.line) + abs(this.col - pos.col)
fun List<Position>.calcArea(): Int = calcPerimeter() / 2 + calcInternalArea() + 1

fun List<Position>.calcInternalArea(): Int = abs((0..this.lastIndex - 1).sumOf { this.determinant(it) }) / 2

fun List<Position>.determinant(idx: Int): Int {
    return this[idx].col * this[idx + 1].line - this[idx + 1].col * this[idx].line
}



