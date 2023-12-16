import Direction.E
import Direction.N
import Direction.S
import Direction.W

fun day16_1(lines: List<String>): Int {
    lines
        .also { printData(lines) }
    notCheckedBeams.add(Beam(E, Position(0, -1)))
    while (lines.runBeam()) { }
    return energized.count() - 1
}

fun day16_2(lines: List<String>): Int = lines.also { printData(it) }.count()


fun Layout.runBeam(): Boolean {
    if (notCheckedBeams.isEmpty()) {
        println("no beams, finish")
        return false
    }
    val beam = notCheckedBeams.first()
    notCheckedBeams.remove(beam)
    checkedBeams.add(beam)
    energized.add(beam.position)
    nextBeams(beam).forEach { newBeam -> if (!checkedBeams.contains(newBeam)) notCheckedBeams.add(newBeam) }
    return notCheckedBeams.isNotEmpty()
}

enum class Direction {
    N, E, W, S;
}

typealias Layout = List<String>

val notCheckedBeams = mutableSetOf<Beam>()
val checkedBeams = mutableSetOf<Beam>()
val energized = mutableSetOf<Position>()
fun Layout.getChar(position: Position) = this[position.line][position.col]
fun Layout.nextBeams(beam: Beam): List<Beam> {
    if (!hasNext(beam)) return emptyList()
    val nextPos = nextPos(beam)
    return when (this.getChar(nextPos)) {
        '.' -> listOf(Beam(beam.direction, nextPos))
        '/' -> when (beam.direction) {
            N -> listOf(Beam(E, nextPos))
            E -> listOf(Beam(N, nextPos))
            S -> listOf(Beam(W, nextPos))
            W -> listOf(Beam(S, nextPos))
        }

        '\\' -> when (beam.direction) {
            N -> listOf(Beam(W, nextPos))
            W -> listOf(Beam(N, nextPos))
            S -> listOf(Beam(E, nextPos))
            E -> listOf(Beam(S, nextPos))
        }

        '|' -> when (beam.direction) {
            N -> listOf(Beam(N, nextPos))
            S -> listOf(Beam(S, nextPos))
            E, W -> listOf(Beam(N, nextPos), Beam(S, nextPos))
        }

        '-' -> when (beam.direction) {
            N, S -> listOf(Beam(E, nextPos), Beam(W, nextPos))
            W -> listOf(Beam(W, nextPos))
            E -> listOf(Beam(E, nextPos))
        }

        else -> emptyList()
    }
}

fun Layout.hasNext(beam: Beam): Boolean = when (beam.direction) {
    N -> beam.position.line > 0
    S -> beam.position.line < this.lastIndex
    E -> beam.position.col < this[beam.position.line].lastIndex
    W -> beam.position.col > 0
}

fun Layout.nextPos(beam: Beam): Position {

    if (!hasNext(beam)) {
        throw IllegalStateException("no next ${beam.direction.name} from $beam.position")
    }
    return when (beam.direction) {
        N -> Position(beam.position.line - 1, beam.position.col)
        S -> Position(beam.position.line + 1, beam.position.col)
        E -> Position(beam.position.line, beam.position.col + 1)
        W -> Position(beam.position.line, beam.position.col - 1)
    }
}

data class Position(val line: Int, val col: Int) {
    override fun toString(): String {
        return "($line, $col)"
    }
}

/**
 * class describes point on the layout and direction the beam came from
 */
data class Beam(val direction: Direction, val position: Position)