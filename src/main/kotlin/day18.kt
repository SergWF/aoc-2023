import Direction.D
import Direction.L
import Direction.R
import Direction.U
import kotlin.math.abs

fun day18_1(lines: List<String>): Long = lines.toTrenchPlan().calcArea()

fun day18_2(lines: List<String>): Long = lines.codesToTrenchPlan()
    .calcArea()

fun List<String>.toTrenchPlan(): List<Position> =
    this.fold(mutableListOf(Position(0, 0))) { acc, line ->
        val s = line.split(" ")
        val dir: Direction = Direction.valueOf(s[0])
        val len = s[1].toInt()
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

fun List<String>.codesToTrenchPlan(): List<Position> =
    this.fold(mutableListOf(Position(0, 0))) { acc, line ->
        val code = line.split("(#", ")")[1]
        val (dir, len) = code.parseCode()
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

fun String.parseCode(): Pair<Direction, Long> = Pair(this.last().toDirection(), this.substring(0, this.lastIndex).toLong(16))
fun Char.toDirection(): Direction =
    when(this) {
        '0' -> R
        '1' -> D
        '2' -> L
        '3' -> U
        else -> throw  IllegalStateException("Wrong direction code: $this")
    }

fun List<Position>.calcPerimeter(): Long = (1..this.lastIndex).sumOf { idx -> this[idx - 1].distance(this[idx]) }

private fun Position.distance(pos: Position): Long = abs(this.line - pos.line) + abs(this.col - pos.col)
fun List<Position>.calcArea(): Long = calcPerimeter() / 2 + calcInternalArea() + 1

fun List<Position>.calcInternalArea(): Long = abs((0..<this.lastIndex).sumOf { this.determinant(it) }) / 2

fun List<Position>.determinant(idx: Int): Long {
    return this[idx].col * this[idx + 1].line - this[idx + 1].col * this[idx].line
}



