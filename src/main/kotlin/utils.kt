import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

val numberRegex = """\d+""".toRegex()

fun String.extractNumbers(): List<Long> = numberRegex.findAll(this).map(MatchResult::value).map { it.toLong() }.toList()
fun readData(taskFile: String): List<String> = Path("src/main/resources/$taskFile").readLines()


fun String.toIntList(): List<Int> = this.trim().split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }
fun Int.pow(x: Int): Int = this.toFloat().pow(x.toFloat()).toInt()

fun <T> List<T>.getFirstIndexOf(start: Int = 0, predicate: (T) -> Boolean): Int = withIndex()
    .filter { predicate(it.value) && it.index > start }
    .map { it.index }
    .firstOrNull()
    ?: this.size

fun String.containsOnly(char: Char) = this.all { it == char }

fun printData(lines: List<String>) {
    val rowCntLen = lines.size.toString().length + 1
    printHeader(lines[0].length, "".padStart(rowCntLen, ' '))
    lines.forEachIndexed { i, line ->
        val idx = i.toString()
        println("${idx.padStart(rowCntLen - idx.length, ' ')}|$line")
    }
}

fun List<String>.pivot(): List<String> =
    (0..this[0].lastIndex).map { idx -> this.map { it[idx] } }.map { it.joinToString("").reversed() }

fun printHeader(colCnt: Int, prefix: String) {
    (0..<colCnt.toString().length).reversed().forEach { r ->
        print(prefix)
        (0..<colCnt).forEach { i -> print(i.get(r)) }
        println()
    }
}

fun Int.length() = when (this) {
    0 -> 1
    else -> log10(abs(toFloat())).toInt() + 1
}

fun Int.get(i: Int, space: Char = ' '): Char {
    val s = this.toString().reversed()
    return if (i > s.lastIndex) space else s[i]
}

fun List<String>.splitByEmptyLine(): List<List<String>> =
    (this).fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, line ->
        if (line.isNotEmpty()) {
            acc.last().add(line)
        } else {
            acc.add(mutableListOf())
        }
        acc
    }

fun Position.down(): Position = Position(this.line + 1, this.col)
fun Position.up(): Position = Position(this.line - 1, this.col)
fun Position.left(): Position = Position(this.line, this.col - 1)
fun Position.right(): Position = Position(this.line, this.col + 1)

data class Position(val line: Long, val col: Long) {
    override fun toString(): String {
        return "($line,$col)"
    }
}

