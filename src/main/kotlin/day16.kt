import MapDirection.E
import MapDirection.N
import MapDirection.S
import MapDirection.W

fun day16_1(lines: List<String>): Int = run(lines, E, Position(0, -1))


fun day16_2(lines: List<String>): Int =
    MapDirection.entries.map { dir ->
        lines.startPositions(dir).map { pos -> run(lines, dir, pos) }
    }.flatten().max()


private fun Layout.startPositions(direction: MapDirection): List<Position> {
    return when (direction) {
        N -> (0..this.last().lastIndex).map { Position(this.lastIndex + 1, it) }
        S -> (0..this.last().lastIndex).map { Position(-1, it) }
        E -> (0..this.lastIndex).map { Position(it, -1) }
        W -> (0..this.lastIndex).map { Position(it, this.lastIndex + 1) }
    }
}

private fun run(lines: List<String>, direction: MapDirection, startPos: Position): Int {
    notCheckedBeams.clear()
    checkedBeams.clear()
    energized.clear()
    notCheckedBeams.add(Beam(direction, startPos))
    while (lines.runBeam()) {
    }
    return energized.count() - 1
}


fun Layout.runBeam(): Boolean {
    if (notCheckedBeams.isEmpty()) return false
    val beam = notCheckedBeams.first()
    notCheckedBeams.remove(beam)
    checkedBeams.add(beam)
    energized.add(beam.position)
    nextBeams(beam).forEach { newBeam -> if (!checkedBeams.contains(newBeam)) notCheckedBeams.add(newBeam) }
    return notCheckedBeams.isNotEmpty()
}

private enum class MapDirection {
    N, E, W, S;
}

typealias Layout = List<String>

private val notCheckedBeams = mutableSetOf<Beam>()
private val checkedBeams = mutableSetOf<Beam>()
private val energized = mutableSetOf<Position>()
private fun Layout.getChar(position: Position) = this[position.line][position.col]
private fun Layout.nextBeams(beam: Beam): List<Beam> {
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

private fun Layout.hasNext(beam: Beam): Boolean = when (beam.direction) {
    N -> beam.position.line > 0
    S -> beam.position.line < this.lastIndex
    E -> beam.position.col < this[beam.position.line].lastIndex
    W -> beam.position.col > 0
}

private fun Layout.nextPos(beam: Beam): Position {

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


/**
 * class describes point on the layout and direction the beam came from
 */
private data class Beam(val direction: MapDirection, val position: Position)