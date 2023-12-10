fun day10_1(lines: List<String>): Int = lines.getWay().size / 2



const val startSign = 'S'
private fun List<String>.getRoomAt(x: Int, y: Int): Room? = if (isValidRoom(x, y)) Room(x, y, this[x][y]) else null



private fun List<String>.findStart(): Room {
    val x = this.indexOfFirst { it.contains(startSign) }
    val y = this[x].indexOf(startSign)
    return Room(x, y, startSign)
}

private fun List<String>.getPossibleWays(room: Room): List<Room> {
    val rooms = when (room.pipeType) {
        '|' -> listOf(getRoomUp(room), getRoomDown(room))
        '-' -> listOf(getRoomLeft(room), getRoomRight(room))
        'L' -> listOf(getRoomUp(room), getRoomRight(room))
        'J' -> listOf(getRoomUp(room), getRoomLeft(room))
        '7' -> listOf(getRoomDown(room), getRoomLeft(room))
        'F' -> listOf(getRoomDown(room), getRoomRight(room))
        startSign -> listOf(getRoomUp(room), getRoomDown(room), getRoomLeft(room), getRoomRight(room))
        else -> emptyList()
    }
    return rooms.filterNotNull().filter { it.pipeType != '.' }
}

private fun List<String>.isValidRoom(x: Int, y: Int): Boolean =
    (x >= 0 && x < this.size && y >= 0 && y < this[0].length)

private fun List<String>.getRoomUp(current: Room): Room? = this.getRoomAt(current.x - 1, current.y)
private fun List<String>.getRoomDown(current: Room): Room? = this.getRoomAt(current.x + 1, current.y)
private fun List<String>.getRoomLeft(current: Room): Room? = this.getRoomAt(current.x, current.y - 1)
private fun List<String>.getRoomRight(current: Room): Room? = this.getRoomAt(current.x, current.y + 1)
private fun List<String>.goNext(current: Room, prev: Room): Room = this.getPossibleWays(current).first { it != prev }

private fun List<String>.getWay(): List<Room> {
    val start = findStart()
    val steps = mutableListOf<Room>()
    var prev = start
    var current = start
    while (steps.isEmpty() || steps.last() != start) {
        steps.add(goNext(current, prev))
        prev = current
        current = steps.last()
    }
    return steps
}

private data class Room(val x: Int, val y: Int, val pipeType: Char) {
    override fun toString(): String {
        return "Room($pipeType: $x,$y)"
    }
}

