fun day14_1(lines: List<String>): Int = lines.toMatrix().rollNorth().northWeight()

fun day14_2(lines: List<String>): Int {
    val matrix = lines.also { printData(it) }.toMatrix()
    return cycles(matrix, 1_000_000_000).northWeight()
}

fun String.rollLeft(): String =
    this.split("#").joinToString("#") { it.toCharArray().sorted().reversed().joinToString("") }

fun String.rollRight(): String = this.split("#").joinToString("#") { it.toCharArray().sorted().joinToString("") }
fun String.weight(): Int = this.mapIndexed { index, c -> if (c == 'O') index + 1 else 0 }.sum()

typealias Matrix = Array<CharArray>

fun List<String>.toMatrix(): Matrix = this.map { it.map { it }.toCharArray() }.toTypedArray()
fun Matrix.getColumn(index: Int): CharArray = this.map { it[index] }.toCharArray()
fun Matrix.rows(): List<CharArray> = this.toList()
fun Matrix.cols(): List<CharArray> = indices.map { this.getColumn(it) }
fun Matrix.replaceRow(rowIndex: Int, data: CharArray): Matrix {
    data.forEachIndexed { charIndex, c ->
        this[rowIndex][charIndex] = c
    }
    return this
}

fun Matrix.replaceCol(colIndex: Int, data: CharArray): Matrix {
    data.forEachIndexed { charIndex, c ->
        this[charIndex][colIndex] = c
    }
    return this
}

fun cycles(source: Matrix, cnt: Int = 1): Matrix {
    val matrix = source.deepClone()
    val calculated = mutableListOf<String>()
    var cycleStart = 0
    val c = (1..cnt).takeWhile {
        val hash = matrix.turn().toHash()
        if (calculated.contains(hash)) {
            cycleStart = calculated.indexOf(hash)
            calculated.add(hash)
            false
        } else {
            calculated.add(hash)
            true
        }
    }
    return if (c.size == cnt) {
        matrix
    } else {
        val cycleLen = calculated.size - cycleStart - 1
        val indexEqualCnt = cycleStart + (cnt - cycleStart) % cycleLen - 1
        val res = calculated[indexEqualCnt]
        res.toMatrix()
    }
}

fun Matrix.rollNorth(): Matrix {
    this.cols().forEachIndexed { colIndex, col -> this.replaceCol(colIndex, col.rollLeft()) }
    return this
}

fun Matrix.rollSouth(): Matrix {
    this.cols().forEachIndexed { colIndex, col -> this.replaceCol(colIndex, col.rollRight()) }
    return this
}

fun Matrix.rollWest(): Matrix {
    this.rows().forEachIndexed { rowIndex, row -> this.replaceRow(rowIndex, row.rollLeft()) }
    return this
}

fun Matrix.rollEast(): Matrix {
    this.rows().forEachIndexed { rowIndex, row -> this.replaceRow(rowIndex, row.rollRight()) }
    return this
}

fun Matrix.asList(): List<String> = this.map { it.joinToString("") }

fun CharArray.rollRight(): CharArray = this.joinToString("").rollRight().toCharArray()
fun CharArray.rollLeft(): CharArray = this.joinToString("").rollLeft().toCharArray()

fun Matrix.northWeight(): Int = this.cols().sumOf { it.reversed().joinToString("").weight() }
fun Matrix.turn(): Matrix = this.rollNorth().rollWest().rollSouth().rollEast()
fun Matrix.toHash(): String = this.joinToString("|") { it.joinToString("") }
fun String.toMatrix() = this.split("|").toMatrix()
fun Matrix.deepClone(): Matrix = this.map { it.clone() }.toTypedArray()